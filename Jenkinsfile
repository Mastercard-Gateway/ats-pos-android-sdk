node {
    stage 'Checkout'
    checkout scm

    stage 'Build'
    sh "./gradlew clean assemble lib:androidSourcesJar lib:dokkaJavadocsJar lib:generatePomFileForAarPublication"

    stage 'Deploy SDK'
    sh "./gradlew lib:artifactoryPublish"

    stage 'Archive'
    step([$class: 'ArtifactArchiver', artifacts: '**/*.aar, **/*.apk', fingerprint: true])
    properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', artifactNumToKeepStr: '10', numToKeepStr: '10']]])

}