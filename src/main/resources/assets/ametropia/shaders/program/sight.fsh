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

const float range = 100;

float scanlines() {
    return sin(gl_FragCoord.y)*0.5+0.5;
}

vec3 worldpos(float depth) {
    float z = depth * 2.0 - 1.0;
    vec4 clipSpacePosition = vec4(texCoord * 2.0 - 1.0, z, 1.0);
    vec4 viewSpacePosition = invProjMat * clipSpacePosition;
    viewSpacePosition /= viewSpacePosition.w;
    vec4 worldSpacePosition = invViewMat * viewSpacePosition;

    return pos + worldSpacePosition.xyz;
}

void main() {
    vec2 oneTexel = 1.0 / InSize;

    float depth = texture2D(depthTex, texCoord).r;
    vec3 pos = worldpos(depth);
    float dist = distance(pos, eyePos);

    float par=1;

    vec4 orC = texture2D(DiffuseSampler, texCoord);

    // if (dist < range  && depth < 1) {
    par=clamp(dist/range, 0, 1);
    //    gl_FragColor = vec4(0, 0, 0, 1);

    //}
    //  gl_FragColor = vec4(orC.rgb, 1);


    int buler=int(30*par);
    int skiped=int(2);


    if (buler/skiped==0){
        gl_FragColor = vec4(texture2D(DiffuseSampler, texCoord).rgb, 1);
        return;
    }

    vec3 base = vec3(0, 0, 0);
    for (int i = 0; i < buler/skiped; i++){
        int ai=i*skiped;
        for (int k = 0; k < buler/skiped; k++){
            int ak=k*skiped;
            vec4 u = texture2D(DiffuseSampler, texCoord + vec2(-oneTexel.x*(buler/2) + oneTexel.x*ai, -oneTexel.y*(buler/2)+ oneTexel.y*ak));
            base.x+=u.x;
            base.y+=u.y;
            base.z+=u.z;
        }
    }

    base/=pow(buler/skiped, 2);

    gl_FragColor = vec4(base, 1);
}