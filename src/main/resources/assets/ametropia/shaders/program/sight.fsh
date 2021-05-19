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

void main() {

    float dist = objectDistance(texCoord);
    float bulerPar=clamp(dist/100, 0, 1);
    float bulerRange=floor(10*bulerPar);
    float bulerSkip=floor(1);

    if (bulerRange/bulerSkip<=0){
        gl_FragColor = vec4(texture2D(DiffuseSampler, texCoord).rgb, 1);
        return;
    }

    vec3 base = vec3(0);
    float avgc=0;

    for (float x = 0; x < bulerRange/bulerSkip; x++){
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

    if (avgc>0){
        base/=avgc;
    } else {
        gl_FragColor = vec4(texture2D(DiffuseSampler, texCoord).rgb, 1);
        return;
    }

    gl_FragColor = vec4(base, 1);
}