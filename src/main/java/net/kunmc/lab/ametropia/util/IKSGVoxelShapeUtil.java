package net.kunmc.lab.ametropia.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import org.apache.commons.lang3.ArrayUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * https://github.com/TeamFelnull/OtyacraftEngine/blob/master/common/src/main/java/red/felnull/otyacraftengine/util/IKSGVoxelShapeUtil.javaからの移植
 * VoxelShapeを簡単に移動や回転をできるようにする
 *
 * @author MORIMORI0317
 * @since 1.0
 */
public class IKSGVoxelShapeUtil {
    private static final Gson gson = new Gson();

    /**
     * VoxelShapeを作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X座標
     * @param y2 終了Y座標
     * @param z2 終了Z座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape makeBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Block.box(x1, y1, z1, x2, y2, z2);
    }

    /**
     * Y90度回転したVoxelShapeを作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X座標
     * @param y2 終了Y座標
     * @param z2 終了Z座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape makeBoxY90(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox(z1, y1, 16 - x1, z2, y2, 16 - x2);
    }

    /**
     * Y180度回転したVoxelShapeを作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X座標
     * @param y2 終了Y座標
     * @param z2 終了Z座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape makeBoxY180(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox(16 - x1, y1, 16 - z1, 16 - x2, y2, 16 - z2);
    }

    /**
     * Y270度回転したVoxelShapeを作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X座標
     * @param y2 終了Y座標
     * @param z2 終了Z座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape makeBoxY270(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY180(z1, y1, 16 - x1, z2, y2, 16 - x2);
    }

    /**
     * VoxelShapeを相対座標で作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X相対座標
     * @param y2 終了Y相対座標
     * @param z2 終了Z相対座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape addBoxY0(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    /**
     * Y90度回転したVoxelShapeを相対座標で作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X相対座標
     * @param y2 終了Y相対座標
     * @param z2 終了Z相対座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape addBoxY90(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY90(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    /**
     * Y180度回転したVoxelShapeを相対座標で作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X相対座標
     * @param y2 終了Y相対座標
     * @param z2 終了Z相対座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape addBoxY180(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY180(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    /**
     * Y270度回転したVoxelShapeを相対座標で作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X相対座標
     * @param y2 終了Y相対座標
     * @param z2 終了Z相対座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape addBoxY270(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY270(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    /**
     * VoxelShapeを合体する
     *
     * @param shapes 合わせるVoxelShape
     * @return 合体したVoxelShape
     */
    public static VoxelShape uniteBox(VoxelShape... shapes) {
        return VoxelShapes.or(shapes[0], ArrayUtils.remove(shapes, 0));
    }

    /**
     * VoxelShapeを移動する
     * 1.0fで16分の１ブロック分移動
     *
     * @param shape 元のVoxelShape
     * @param x     X方向
     * @param y     Y方向
     * @param z     Z方向
     * @return 移動し終わったあとのVoxelShape
     */
    public static VoxelShape moveBox(VoxelShape shape, double x, double y, double z) {
        VoxelShape[] shapes = {};
        for (AxisAlignedBB aabb : shape.toAabbs()) {
            shapes = ArrayUtils.add(shapes, VoxelShapes.create(aabb.move(1d / 16d * x, 1d / 16d * y, 1d / 16d * z)));
        }
        return uniteBox(shapes);
    }

    /**
     * VoxelShapeをY90度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBoxY90(VoxelShape shape) {
        VoxelShape[] shapes = {};
        for (AxisAlignedBB aabb : shape.toAabbs()) {
            shapes = ArrayUtils.add(shapes, VoxelShapes.create(new AxisAlignedBB(aabb.minZ, aabb.minY, 1 - aabb.minX, aabb.maxZ, aabb.maxY, 1 - aabb.maxX)));
        }
        return uniteBox(shapes);
    }

    /**
     * VoxelShapeをY180度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBoxY180(VoxelShape shape) {
        VoxelShape[] shapes = {};
        for (AxisAlignedBB aabb : shape.toAabbs()) {
            shapes = ArrayUtils.add(shapes, VoxelShapes.create(new AxisAlignedBB(1 - aabb.minX, aabb.minY, 1 - aabb.minZ, 1 - aabb.maxX, aabb.maxY, 1 - aabb.maxZ)));
        }
        return uniteBox(shapes);
    }

    /**
     * VoxelShapeをY270度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBoxY270(VoxelShape shape) {
        VoxelShape[] shapes = {};
        for (AxisAlignedBB aabb : rotateBoxY180(shape).toAabbs()) {
            shapes = ArrayUtils.add(shapes, VoxelShapes.create(new AxisAlignedBB(aabb.minZ, aabb.minY, 1 - aabb.minX, aabb.maxZ, aabb.maxY, 1 - aabb.maxX)));
        }
        return uniteBox(shapes);
    }

    /**
     * VoxelShapeをX90度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxX90(VoxelShape shape) {
        VoxelShape[] shapes = {};
        for (AxisAlignedBB aabb : shape.toAabbs()) {
            shapes = ArrayUtils.add(shapes, VoxelShapes.create(new AxisAlignedBB(aabb.minY, aabb.minX, aabb.minZ, aabb.maxY, aabb.maxX, aabb.maxZ)));
        }
        return uniteBox(shapes);
    }

    /**
     * VoxelShapeをX180度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxX180(VoxelShape shape) {
        VoxelShape[] shapes = {};
        for (AxisAlignedBB aabb : shape.toAabbs()) {
            shapes = ArrayUtils.add(shapes, VoxelShapes.create(new AxisAlignedBB(1 - aabb.minX, 1 - aabb.minY, aabb.minZ, 1 - aabb.maxX, 1 - aabb.maxY, aabb.maxZ)));
        }
        return uniteBox(shapes);
    }

    /**
     * VoxelShapeをX270度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxX270(VoxelShape shape) {
        VoxelShape[] shapes = {};
        for (AxisAlignedBB aabb : rotateBoxY180(shape).toAabbs()) {
            shapes = ArrayUtils.add(shapes, VoxelShapes.create(new AxisAlignedBB(1 - aabb.minY, aabb.minX, aabb.minZ, 1 - aabb.maxY, aabb.maxX, aabb.maxZ)));
        }
        return uniteBox(shapes);
    }

    /**
     * VoxelShapeをZ90度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxZ90(VoxelShape shape) {
        VoxelShape[] shapes = {};
        for (AxisAlignedBB aabb : shape.toAabbs()) {
            shapes = ArrayUtils.add(shapes, VoxelShapes.create(new AxisAlignedBB(aabb.minX, aabb.minZ, aabb.minY, aabb.maxX, aabb.maxZ, aabb.maxY)));
        }
        return uniteBox(shapes);
    }

    /**
     * VoxelShapeをZ180度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxZ180(VoxelShape shape) {
        VoxelShape[] shapes = {};
        for (AxisAlignedBB aabb : shape.toAabbs()) {
            shapes = ArrayUtils.add(shapes, VoxelShapes.create(new AxisAlignedBB(aabb.minX, 1 - aabb.minY, 1 - aabb.minZ, aabb.maxX, 1 - aabb.maxY, 1 - aabb.maxZ)));
        }
        return uniteBox(shapes);
    }

    /**
     * VoxelShapeをDirectionの方向に回転する
     *
     * @param shape     回転するVoxelShape
     * @param direction 回転する方向のDirection
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBoxDirection(VoxelShape shape, Direction direction) {
        switch (direction) {
            case SOUTH:
                return rotateBoxY180(shape);
            case EAST:
                return rotateBoxY270(shape);
            case WEST:
                return rotateBoxY90(shape);
            default:
                return shape;
        }
    }

    public static VoxelShape getShapeFromJson(JsonObject shapeJ) {
        VoxelShape[] shapes = null;
        for (JsonElement jshape : shapeJ.getAsJsonArray("shapes")) {
            JsonArray ja = jshape.getAsJsonArray();
            VoxelShape shape = makeBox(ja.get(0).getAsDouble(), ja.get(1).getAsDouble(), ja.get(2).getAsDouble(), ja.get(3).getAsDouble(), ja.get(4).getAsDouble(), ja.get(5).getAsDouble());
            shapes = ArrayUtils.add(shapes, shape);
        }
        return uniteBox(shapes);
    }

    public static VoxelShape getShapeFromResource(ResourceLocation location) {
        InputStream stream = IKSGVoxelShapeUtil.class.getResourceAsStream("/data/" + location.getNamespace() + "/shape/" + location.getPath() + ".json");
        if (stream == null) {
            return makeBox(16, 16, 16, 16, 16, 16);
        }
        return getShapeFromJson(gson.fromJson(new InputStreamReader(stream, StandardCharsets.UTF_8), JsonObject.class));
    }
}