node {
    stage 'Checkout'
    checkout scm

    stage 'Build'
    sh "./gradlew clean build"

    stage 'Deploy SDK'
    sh "./gradlew lib:generatePomFileForAarPublication lib:artifactoryPublish"

    stage 'Archive'
    step([$class: 'ArtifactArchiver', artifacts: '**/*.aar', fingerprint: true])
    properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', artifactNumToKeepStr: '10', numToKeepStr: '10']]])

}