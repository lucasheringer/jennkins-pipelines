pipeline {
  agent any
  parameters{
    extendedChoice bindings: '', description: 'choose environment to deploy to', groovyClasspath: '', groovyScript: '''import hudson.model.*
    NODE=Hudson.instance.getRootUrl().split('/')[2]

    if ( NODE =~ /127.0.0.1:8080/ ){
      return[\'ENV1\', \'ENV2\']
    } else if (NODE =~ /another.jenkins.url:8080/ ){
      return[\'another_env\']
    } else if (NODE =~ /someting.jenkins.com:8080/ ){
      return[\'last_env\']
    }
      return[\'unknown jenkins server\']''', multiSelectDelimiter: ',', name: 'BT_ENV', quoteValue: false, saveJSONParameterToFile: false, type: 'PT_SINGLE_SELECT', visibleItemCount: 1

  }
  stages{
    stage('Doing something'){
      steps{
        script{
          println("Do something with the variables selected!")
        }
      }
    }
  }
}
