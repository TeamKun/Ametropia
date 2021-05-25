#version 130

uniform mat4 invViewMat;
uniform mat4 invProjMat;
uniform sampler2D depthTex;
uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;
uniform vec2 InSize;
uniform vec3 pos;

uniform float focus;//焦点距離
uniform float range;//焦点範囲
uniform float difference;//焦点の差
uniform float ignoreDist;//最低ぼやけ無視距離

uniform float level;//ぼやけ

varying vec2 texCoord;
varying vec2 oneTexel;

vec3 worldpos(float depth) {
    float z = depth * 2.0 - 1.0;
    vec4 clipSpacePosition = vec4(texCoord * 2.0 - 1.0, z, 1.0);
    vec4 viewSpacePosition = invProjMat * clipSpacePosition;
    viewSpacePosition /= viewSpacePosition.w;
    vec4 worldSpacePosition = invViewMat * viewSpacePosition;
    return pos + worldSpacePosition.xyz;
}

float objectDistance(vec2 pixpos){
    float depth = texture2D(depthTex, pixpos).r;
    vec3 wpos = worldpos(depth);
    float dist = distance(wpos, pos);
    return dist;
}

vec3 bulerd(float par){
    float samplete=par*(5+level*10);
    float skiped=samplete/5;

    vec3 base = texture2D(DiffuseSampler, texCoord).rgb;
    float avgc=1;

    for (float x = -samplete; x < samplete; x+=skiped){
        for (float y = -samplete; y < samplete; y+=skiped){
            vec2 spp=texCoord + vec2(oneTexel.x*x, oneTexel.y*y);

            vec4 uc = texture2D(DiffuseSampler, spp);
            base+=uc.rgb;

            avgc++;
        }
    }

    base/=avgc;
    return base;
}

void main() {
    float dist = objectDistance(texCoord);
    float bulerPar=clamp(dist/100, 0, 1);
    //  float bulerRange=15*bulerPar;
    //  float bulerSkip=1;

    //   float blCont=bulerRange/bulerSkip;
    /*
       vec3 base = texture2D(DiffuseSampler, texCoord).rgb;
       float avgc=1;

       for (float x = 0; x < blCont; x++){
            float ax =x*bulerSkip;
            for (float y = 0; y < bulerRange/bulerSkip; y++){
                float ay=y*bulerSkip;
                vec2 up=texCoord + vec2(-oneTexel.x*(bulerRange/2) + oneTexel.x*ax, -oneTexel.y*(bulerRange/2)+ oneTexel.y*ay);
                float ud= objectDistance(up);

                if (ignoreDist<=ud||dist<=ud){
                    vec4 uc = texture2D(DiffuseSampler, up);
                    base+=uc.rgb;
                    avgc++;
                }
            }
        }

        base/=avgc;*/

    gl_FragColor = vec4(bulerd(bulerPar).rgb, 1);
}