node {
    stage 'Checkout'
    checkout scm

    stage 'Unit Test'
    // sh "./gradlew clean --refresh-dependencies lintDebug testDebugUnitTest"
    // sh "./gradlew clean --refresh-dependencies testDebugUnitTest"

    stage 'Assemble'
    sh "./gradlew assemble generateFingerprints"

    stage 'Archive'
    androidLint canComputeNew: false, canRunOnFailed: true, defaultEncoding: '', failedNewHigh: '0', healthy: '', pattern: 'app/build/**/lint-*.xml', unHealthy: '', unstableTotalAll: '200'
    step([$class: 'JUnitResultArchiver', testResults: 'app/build/test-results/**/TEST-*.xml'])
    step([$class: 'ArtifactArchiver', artifacts: 'app/build/outputs/**/*.apk,app/build/outputs/**/*.sha256', fingerprint: true])
    properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', artifactNumToKeepStr: '10', numToKeepStr: '10']]])
}