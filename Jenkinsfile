node {
    stage 'Checkout'
    checkout scm

    stage 'Test'
    sh "./gradlew clean --refresh-dependencies lib:lintRelease lib:testReleaseUnitTest"

    stage 'Build'
    sh "./gradlew assemble lib:androidSourcesJar lib:dokkaJavadocsJar lib:generatePomFileForAarPublication"

    stage 'Deploy SDK'
    sh "./gradlew lib:artifactoryPublish"

    stage 'Archive'
    step([$class: 'ArtifactArchiver', artifacts: '**/build/outputs/**/*.aar, **/build/outputs/**/*.apk', fingerprint: true])
    properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', artifactNumToKeepStr: '10', numToKeepStr: '10']]])

}