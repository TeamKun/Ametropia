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

float objectDistance(float depth){
    vec3 wpos = worldpos(depth);
    float dist = distance(wpos, pos);
    return dist;
}

vec3 ikisugiBuler(float blevel, float dist, float bdepth){
    vec3 col = vec3(0., 0., 0.);
    float weight_total = 0.;
    float ignoreDist=60-(30*blevel);

    float blur =10*blevel*level;
    if (bdepth < 1||dist>=ignoreDist){
        for (float y = -blur; y <= blur; y += 1.){
            vec2 colp=vec2(texCoord+vec2(0, y*oneTexel.y));
            float depth = texture2D(depthTex, colp).r;
            float ud=objectDistance(depth);
            if ((dist>=ignoreDist&&ignoreDist<=ud)||dist<=ud){
                float distance_normalized = abs(y / blur);
                float weight = exp(-0.5 * pow(distance_normalized, 2.) * 5.0);
                weight_total += weight;
                col += texture(DiffuseSampler, colp).rgb * weight;
            }
        }
    }
    col /= weight_total;

    return col;
}

void main() {
    float depth = texture2D(depthTex, texCoord).r;

    float dist = objectDistance(depth);
    float bulerPar=clamp(dist/100, 0, 1);
    gl_FragColor = vec4(ikisugiBuler(bulerPar*10, dist, depth), 0);
}