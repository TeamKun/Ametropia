#version 130

uniform mat4 invViewMat;
uniform mat4 invProjMat;
uniform sampler2D depthTex;
uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;
uniform vec2 InSize;
uniform vec3 pos;
uniform vec3 eyePos;

uniform float focus;//焦点距離
uniform float range;//焦点範囲
uniform float difference;//焦点の差

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
    vec3 pos = worldpos(depth);
    float dist = distance(pos, eyePos);
    return dist;
}

void main() {
    float dist = objectDistance(texCoord);
    //  vec4 orginal = texture2D(DiffuseSampler, texCoord);

    /*float f1=dist-(focus-range/2);
    float f2=f1/range;
    float f3=clamp(f2, 0, 1);
    float f4=abs(0.5f-f3)*2;*/

    float bulerPar=clamp(dist/100, 0, 1);

    float bulerRange=floor(30*bulerPar);
    float bulerSkip=floor(2);

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
            if (dist<=ud){
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