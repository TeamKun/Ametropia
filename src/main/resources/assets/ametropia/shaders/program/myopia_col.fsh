#version 130

uniform mat4 invViewMat;
uniform mat4 invProjMat;
uniform sampler2D depthTex;
uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;
uniform vec2 InSize;
uniform vec3 pos;

uniform float level;
uniform float range;
uniform float renderDistance;

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

float objectDistance(float depth){
    vec3 wpos = worldpos(depth);
    float dist = distance(wpos, pos);
    return dist;
}

vec3 ikisugiBuler(float dist, float depth){
    vec3 col = vec3(0, 0, 0);
    float par=clamp((dist-range)/(range/2), 0, 1);//     (dist-range)/renderDistance;
    if (dist>=range){
        col=vec3(par, 0, 0);
    }

    return col;
}

void main() {
    float depth = texture2D(depthTex, texCoord).r;
    float dist = objectDistance(depth);
    gl_FragColor = vec4(ikisugiBuler(dist, depth), 0);
}