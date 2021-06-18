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

vec4 bulerd(float par, float dist, float Bdepth){


    vec2 p = texCoord;
    vec4 orgin=texture(DiffuseSampler, p);



    float blur =30;

    vec4 col = vec4(0., 0., 0., 0.);
    float weight_total = 0.;
    for (float x = -blur; x <= blur; x += 1.){
        float distance_normalized = abs(x / blur);
        float weight = exp(-0.5 * pow(distance_normalized, 2.) * 5.0);
        weight_total += weight;
        col += texture(DiffuseSampler, p+vec2(x*oneTexel.x, 0.)) * weight;
    }
    col /= weight_total;

    weight_total=0.;
    vec4 col2 = vec4(0., 0., 0., 0.);

    for (float y = -blur; y <= blur; y += 1.){
        float distance_normalized = abs(y / blur);
        float weight = exp(-0.5 * pow(distance_normalized, 2.) * 5.0);
        weight_total += weight;
        col2 += texture(DiffuseSampler, p+vec2(0., y*oneTexel.y)) * weight;
    }
    col2 /= weight_total;



    return col;//vec4((col+col2)/2.);

    /*
     if (Bdepth < 1||dist>=ignoreDist){
         for (float x = -samplete; x < samplete; x+=skiped){
             for (float y = -samplete; y < samplete; y+=skiped){
                 vec2 spp=texCoord + vec2(oneTexel.x*x, oneTexel.y*y);
                 float depth = texture2D(depthTex, spp).r;
                 float ud=objectDistance(depth);
                 if ((dist>=ignoreDist&&ignoreDist<=ud)||dist<=ud){
                     vec4 uc = texture2D(DiffuseSampler, spp);
                     base+=uc.rgb;
                     avgc++;
                 }
             }
         }
     }
     */
    //   base/=avgc;
    // return base;
}

void main() {
    float depth = texture2D(depthTex, texCoord).r;

    float dist = objectDistance(depth);
    float bulerPar=clamp(dist/100, 0, 1);
    gl_FragColor = vec4(bulerd(bulerPar, dist, depth));
}