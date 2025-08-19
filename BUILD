load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "android-mms-lib",
    srcs = glob(["src/main/java/**/*.java"]),
    visibility = ["//visibility:public"],
    deps = [
        "@maven_android_mms//:org_slf4j_slf4j_api",
    ],
)
