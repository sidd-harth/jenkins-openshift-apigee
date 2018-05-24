# CI/CD - Openshift Jenkins Apigee Demo 
----------------------------------------
![jenkins3](https://user-images.githubusercontent.com/28925814/40500583-db81e0bc-5fa2-11e8-9604-2b4d02fe1f90.jpg)
### This is an extension of [AWS-Openhsift-Enterprise-Jenkins Pipeline](https://github.com/sidd-harth/openshift-AWS-jenkins) which is in-turn a modified/simplified version of [Openshift-Origin-Jenkins Pipeline](https://github.com/sidd-harth/openshift-jenkins)
--------------------------------------------------------------------------------------------------------------------------------------
## For detailed documentation please visit,  [Openshift-Origin-JenkinsPipeline](https://github.com/sidd-harth/openshift-jenkins)
-------------------------------------------------------------------------------------------------------------------------------

1. As mentioned earlier this **`repo`** is an an extension of [AWS-Openhsift-Enterprise-Jenkins Pipeline](https://github.com/sidd-harth/openshift-AWS-jenkins).
2. After deploying the Application to Openshift, I have added another **`Pipeline Step`** to deploy an Apigee Proxy.
3. I am making use of **`Apigee-Maven-Plugin`** to deploy to Apigee Cloud Instance.
4. Openshift internally uses **`Kubernates`**, all similar **`Pods`** are internally load balanced via a Service Layer which can be linked to a **`Route`** to access data.
5. This Route can be **`pre-defined`** in Openshift such as,
``` http(s)://<application-name>-<project>.<default-domain-suffix> ```
6. Step 4 is completely related to Apigee Maven Plugin, I am only replacing the target value in the **`config.json`** with a pre-defined Openshift Route.
7. I have used an **`Parameterized Build`** to pass all the required fields,
![jenkins2](https://user-images.githubusercontent.com/28925814/40500582-db53759c-5fa2-11e8-8ea3-fa77aea4c3a5.jpg)
----
![jenkins1](https://user-images.githubusercontent.com/28925814/40500581-db239c5a-5fa2-11e8-8c67-28fd99d31c23.jpg)
