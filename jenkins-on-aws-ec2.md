## Jenkins CI/CD Setup Guide

This guide provides step-by-step instructions to set up a Jenkins CI/CD pipeline on an EC2 Linux machine.

### 1. Launch EC2 Linux Machine

Ensure that you have an EC2 Linux machine available for Jenkins installation.

### 2. Clone Code from GitHub

Clone the repository that contains your application code:

```bash
git clone https://github.com/your-username/your-repo.git
```

### 3. Update the package information on a Linux Machine Then Install Docker and docker-compose

```bash
sudo apt update
sudo apt install docker.io
docker --version
```

```bash
sudo apt install docker-compose
```

```bash
docker ps  # (May result in Permission denied error)
whoami  # (Check the current user, likely 'ubuntu')
sudo usermod -aG docker $USER

```

```bash
sudo reboot
```

After reboot, reconnect to the machine and check Docker status:

```bash
docker ps
```

### 4. Command to Open Any File in Linux

Use commands like `cat` or `nano` to open files in Linux:

```bash
cat filename

```

# or

```bash
nano filename

```

ctrl+O then click Enter then ctrl+exit

### 5. Install Jenkins

#### Step 1: Install Java

```bash
sudo apt update
sudo apt install openjdk-17-jre
java --version
```

#### Step 2: Install Jenkins

Follow the official Jenkins download guide:

```bash
sudo wget -O /usr/share/keyrings/jenkins-keyring.asc \
    https://pkg.jenkins.io/debian-stable/jenkins.io-2023.key
```

```bash
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
    https://pkg.jenkins.io/debian-stable binary/ | sudo tee \
    /etc/apt/sources.list.d/jenkins.list > /dev/null
```

```bash
sudo apt update
sudo apt install jenkins
```

Check Jenkins service status and set up admin user:

```bash
service jenkins status
```

Follow the instructions to complete the setup

```bash
sudo usermod -aG docker jenkins
sudo reboot
```

After reboot, reconnect to the machine
Allow port 8080 in inbound rule for security group by using MyIP
then try to connect port 8080 using public ip address

```bash
sudo cat /var/lib/jenkins/secrets/initialAdminPassword  # Copy the password

```

### Process to Setup Environment Variable in Jenkins

1. Go to Jenkins Dashboard
2. Navigate to "Manage Jenkins" -> "Credentials"
3. Click "System" -> "Global Credentials"
4. Add credentials for Docker Hub (Username and Password)

### Process to Create Pipeline in Jenkins

1. Go to Jenkins Dashboard
2. Click "Add Item"
3. Provide a name, and select "Pipeline"
4. Write a description, set up the pipeline, write the script, and save.

## grovey syntax for pipeline

```bash
pipeline {
    agent any

    stages {

        stage('Cleanup Workspace') {
            steps {
                // Clean up the workspace to start with a clean slate
                cleanWs()
            }
        }


        stage('Clone Code') {
            steps {
                // Your build steps here
                echo 'Cloning The Code ...'
                git url: "https://github.com/NomadicSatyam/Student-Registration-CRUD-Project.git" , branch:"dev"
            }
        }
        stage('Build') {
            steps {
                // Your build steps here
                echo 'Building The Docker Image...'
                sh "docker build -t my-note-app ."
            }
        }
        stage('Push the DockerHub') {
    steps {
        echo 'Pushing the image into Docker Hub...'
        withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPass', usernameVariable: 'dockerHubUser')]) {
            sh "docker tag my-note-app ${env.dockerHubUser}/student-registration-crud-project:03"
            sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPass}"
            sh "docker push ${env.dockerHubUser}/student-registration-crud-project:03"
        }
    }
}

        stage('Deploy') {
            steps {
                // Your deployment steps here
                echo 'Deploying The Container...'
                //sh "docker run -d -p 8000:8000 satyambrother/my-note-app:latest"
                sh "docker-compose down && docker-compose up -d"
            }
        }
    }
}

```

Now, Jenkins CI/CD pipeline is set up and ready for use. Adjust the steps and commands according to your specific requirements and configurations.
