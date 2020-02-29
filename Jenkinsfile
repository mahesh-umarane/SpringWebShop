node {
   def mvnHome
   stage('First Stage') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/mahesh-umarane/SpringWebShop'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.   
   }
   stage('Second stage') {
      mvnHome = tool 'M2_HOME'
      // Run the maven build
      withEnv(["MVN_HOME=$mvnHome"]) {
         if (isUnix()) {
            sh '"$MVN_HOME/bin/mvn" -Dmaven.test.failure.ignore clean install compile package'
         } else {
            bat(/"%MVN_HOME%\bin\mvn" -Dmaven.test.failure.ignore clean package/)
         }
      }
   }
   stage('deploy') {
      sshPublisher(publishers: [sshPublisherDesc(configName: 'Main server', transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand: 'ansible-playbook -i /etc/ansible/hosts /usr/local/ansible-test/ans-copy.yml ;', execTimeout: 120000, flatten: false, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: '', remoteDirectorySDF: false, removePrefix: '', sourceFiles: '**/*.war')], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: false)])
   }
   stage('Results') {
      echo "Executed successfully"
   }
}
