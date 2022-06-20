properties([pipelineTriggers([githubPush()])])

node {
    git url: 'git@github.com:zxkfall/maplebill-backend.git', branch: 'main'
    stage('Checkout'){
        sh 'echo "This is updated"'
        sh 'java -version'
            }
    stage('Build'){
        sh 'echo "Building"'
    }
    stage('Deploy'){
        sh 'echo "Finish"'
    }
}
