package net.kunmc.lab.ametropia.block;

import net.kunmc.lab.ametropia.Ametropia;
import net.kunmc.lab.ametropia.util.IKSGVoxelShapeUtil;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class SightTestChartBlock extends Block {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    private static final VoxelShape UP_AABB = IKSGVoxelShapeUtil.getShapeFromResource(new ResourceLocation(Ametropia.MODID, "sight_test_chart_up"));
    private static final VoxelShape UP_AABB_90 = IKSGVoxelShapeUtil.rotateBoxY90(UP_AABB);
    private static final VoxelShape DOWN_AABB = IKSGVoxelShapeUtil.getShapeFromResource(new ResourceLocation(Ametropia.MODID, "sight_test_chart_down"));
    private static final VoxelShape DOWN_AABB_90 = IKSGVoxelShapeUtil.rotateBoxY90(DOWN_AABB);

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        DoubleBlockHalf half = state.getValue(HALF);
        Direction direction = state.getValue(FACING);

        if (half == DoubleBlockHalf.UPPER) {
            return direction.getAxis() == Direction.Axis.Z ? UP_AABB : UP_AABB_90;
        } else {
            return direction.getAxis() == Direction.Axis.Z ? DOWN_AABB : DOWN_AABB_90;
        }
    }

    public SightTestChartBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public void setPlacedBy(World p_180633_1_, BlockPos p_180633_2_, BlockState p_180633_3_, @Nullable LivingEntity p_180633_4_, ItemStack p_180633_5_) {
        p_180633_1_.setBlock(p_180633_2_.above(), p_180633_3_.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        BlockPos blockpos = p_196260_3_.below();
        BlockState blockstate = p_196260_2_.getBlockState(blockpos);
        return p_196260_1_.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(p_196260_2_, blockpos, Direction.UP) : blockstate.is(this);
    }

    @Override
    public BlockState updateShape(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
        DoubleBlockHalf doubleblockhalf = p_196271_1_.getValue(HALF);
        if (p_196271_2_.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (p_196271_2_ == Direction.UP)) {
            return p_196271_3_.is(this) && p_196271_3_.getValue(HALF) != doubleblockhalf ? p_196271_1_.setValue(FACING, p_196271_3_.getValue(FACING)) : Blocks.AIR.defaultBlockState();
        } else {
            return doubleblockhalf == DoubleBlockHalf.LOWER && p_196271_2_ == Direction.DOWN && !p_196271_1_.canSurvive(p_196271_4_, p_196271_5_) ? Blocks.AIR.defaultBlockState() : super.updateShape(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        BlockPos blockpos = p_196258_1_.getClickedPos();
        if (blockpos.getY() < 255 && p_196258_1_.getLevel().getBlockState(blockpos.above()).canBeReplaced(p_196258_1_)) {
            World world = p_196258_1_.getLevel();
            boolean flag = world.hasNeighborSignal(blockpos) || world.hasNeighborSignal(blockpos.above());
            return this.defaultBlockState().setValue(FACING, p_196258_1_.getHorizontalDirection()).setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public long getSeed(BlockState p_209900_1_, BlockPos p_209900_2_) {
        return MathHelper.getSeed(p_209900_2_.getX(), p_209900_2_.below(p_209900_1_.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), p_209900_2_.getZ());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(HALF, FACING);

    }

    @Override
    public void playerWillDestroy(World p_176208_1_, BlockPos p_176208_2_, BlockState p_176208_3_, PlayerEntity p_176208_4_) {
        if (!p_176208_1_.isClientSide && p_176208_4_.isCreative()) {
            DoublePlantBlock.preventCreativeDropFromBottomPart(p_176208_1_, p_176208_2_, p_176208_3_, p_176208_4_);
        }

        super.playerWillDestroy(p_176208_1_, p_176208_2_, p_176208_3_, p_176208_4_);
    }

    @Override
    public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
        return p_185499_1_.setValue(FACING, p_185499_2_.rotate(p_185499_1_.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
        return p_185471_2_ == Mirror.NONE ? p_185471_1_ : p_185471_1_.rotate(p_185471_2_.getRotation(p_185471_1_.getValue(FACING)));
    }


}
