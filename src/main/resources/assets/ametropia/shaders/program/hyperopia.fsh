#version 130

uniform mat4 invViewMat;
uniform mat4 invProjMat;
uniform sampler2D depthTex;
uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;
uniform vec2 InSize;
uniform vec3 pos;

uniform float level;

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

vec3 bulerd(float par, float dist){
    float samplete=par*(5+level*10);
    float skiped=samplete/5;

    vec3 org=texture2D(DiffuseSampler, texCoord).rgb;
    vec3 base = org;
    float avgc=1;

    for (float x = -samplete; x < samplete; x+=skiped){
        for (float y = -samplete; y < samplete; y+=skiped){
            vec2 spp=texCoord + vec2(oneTexel.x*x, oneTexel.y*y);

            float ud=objectDistance(spp);

            if (dist>=0||dist<=ud){
                vec4 uc = texture2D(DiffuseSampler, spp);
                base+=uc.rgb;
            } else {
                base+=org;
            }
            avgc++;
        }
    }

    base/=avgc;
    return base;
}

void main() {
    float dist = objectDistance(texCoord);
    float bulerPar=clamp(dist/100, 0, 1);
    gl_FragColor = vec4(bulerd(1-bulerPar, dist).rgb, 1);
}