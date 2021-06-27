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
float getPar(float dist){
    return clamp((dist-range)/(range/1.5), 0, 1);
}

vec3 bulered(float par){
    vec3 col = vec3(0, 0, 0);
    float blur =par;
    float weight_total = 0;

    for (float y = -blur; y <= blur; y += 1.){

        vec2 colp=vec2(texCoord+vec2(0, y*oneTexel.y));

        float depth2 = texture2D(depthTex, colp).r;
        float ud=objectDistance(depth2);
        if (ud>range&&getPar(ud)<=par){
            float distance_normalized = abs(y / blur);
            float weight = exp(-0.5 * pow(distance_normalized, 2.) * 5.0);
            weight_total += weight;
            col += texture(DiffuseSampler, colp).rgb * weight;
        }
    }
    col /= weight_total;

    return col;
}
void main() {
    float depth = texture2D(depthTex, texCoord).r;
    float dist = objectDistance(depth);

    float par=getPar(dist);

    vec3 bcol=vec3(0, 0, 0);

    if (dist<range){
        bcol=texture(DiffuseSampler, texCoord).rgb;
    } else {
        bcol=bulered(par*level);
    }

    gl_FragColor = vec4(bcol, 0);
}