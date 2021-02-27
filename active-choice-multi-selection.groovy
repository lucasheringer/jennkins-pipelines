#!/usr/bin/env groovy
// Define variables
List category_list = ["\"Select:selected\"","\"EU-Region\"","\"US-Region\"","\"APAC-Region\""]
List EU_list = ["\"Select:selected\"","\"Ireland\"","\"London\"","\"Frankfurt\""]
List US_list = ["\"Select:selected\"","\"Ohio\"","\"Oregon\"","\"Central\"" ]
List APAC_list = ["\"Select:selected\"","\"Beijing\"","\"Sydney\"","\"Osaka\""]
List default_item = ["\"Not Applicable\""]
String categories = buildScript(category_list)
String java = buildScript(EU_list)
String jboss = buildScript(US_list)
String php7 = buildScript(APAC_list)
String items = populateItems(default_item,EU_list, US_list, APAC_list)
// Methods to build groovy scripts to populate data
String buildScript(List values){
  return "return $values"
}
String populateItems(List default_item, List EUList, List USList, List APACList){
return """if(Categories.equals('EU-Region')){
     return $EUList
     }
     else if(Categories.equals('US-Region')){
     return $USList
     }else if(Categories.equals('APAC-Region')){
     return $APACList
     }else{
     return $default_item
     }
     """
}
properties([
    parameters([
        [$class: 'ChoiceParameter', choiceType: 'PT_SINGLE_SELECT', name: 'Categories', script:
        [$class: 'GroovyScript', fallbackScript: [classpath: [], sandbox: false, script: 'return ["ERROR"]'], script:
        [classpath: [], sandbox: false, script:  categories]]],

        [$class: 'CascadeChoiceParameter', choiceType: 'PT_SINGLE_SELECT',name: 'REGION_NAME', referencedParameters: 'Categories', script:
        [$class: 'GroovyScript', fallbackScript:
        [classpath: [], sandbox: false, script: 'return ["error"]'], script: [classpath: [], sandbox: false, script: items]]]
    ])
])

pipeline {
  agent any

  stages{
    stage('Doing something'){
      steps{
        script{
          println("Do something with the variables selected!")
          println("Category selected: ${Categories}")
          println("Region selected: ${REGION_NAME}")
        }
      }
    }
  }
}
