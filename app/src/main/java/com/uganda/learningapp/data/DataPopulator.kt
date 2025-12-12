package com.uganda.learningapp.data

import com.uganda.learningapp.data.dao.RoadmapDao
import com.uganda.learningapp.data.entity.ModuleEntity
import com.uganda.learningapp.data.entity.TaskEntity
import com.uganda.learningapp.data.entity.WeekUnitEntity
import com.uganda.learningapp.data.entity.ProjectEntity
import com.uganda.learningapp.data.entity.QuizEntity

object DataPopulator {
    suspend fun populate(dao: RoadmapDao) {
        // Quizzes for Week 1
        dao.insertQuiz(QuizEntity(weekId = 1, question = "What is an IP address?", optionA = "Internet Protocol", optionB = "Internal Post", optionC = "Indian Potato", optionD = "None", correctAnswerIndex = 0))
        dao.insertQuiz(QuizEntity(weekId = 1, question = "Which port is SSH?", optionA = "80", optionB = "22", optionC = "443", optionD = "21", correctAnswerIndex = 1))

        // Projects
        dao.insertProject(ProjectEntity(1, "Home Network Scan", "Scan home network for open ports using Nmap", "Phase 2"))
        dao.insertProject(ProjectEntity(2, "Simple Smart Contract", "Deploy a Hello World contract on Ropsten testnet", "Phase 2"))
        dao.insertProject(ProjectEntity(3, "Crypto Price Bot", "Python script to fetch prices from Binance API", "Phase 3"))

        // Phase 1
        val phase1 = ModuleEntity(1, "Phase 1: Foundations", "Build core technical skills.", "Weeks 1-12")
        dao.insertModule(phase1)

        // Weeks 1-2
        val w1 = WeekUnitEntity(1, 1, "IT Support & Networking", "Weeks 1-2", "Understand how computers and networks work.")
        dao.insertWeek(w1)
        dao.insertTask(TaskEntity(weekId = 1, description = "Install Ubuntu or Kali Linux VM"))
        dao.insertTask(TaskEntity(weekId = 1, description = "Complete Google IT Support Certificate (Module 1)"))
        dao.insertTask(TaskEntity(weekId = 1, description = "Practice Subnetting"))

        // Weeks 3-4
        val w2 = WeekUnitEntity(2, 1, "Linux & OS Basics", "Weeks 3-4", "Master the command line.")
        dao.insertWeek(w2)
        dao.insertTask(TaskEntity(weekId = 2, description = "Learn file permissions (chmod, chown)"))
        dao.insertTask(TaskEntity(weekId = 2, description = "Write a simple Bash script"))

        // Phase 2
        val phase2 = ModuleEntity(2, "Phase 2: Core Skills", "Hacking and Blockchain Dev.", "Weeks 13-24")
        dao.insertModule(phase2)
        
        // Weeks 13-14
        val w3 = WeekUnitEntity(3, 2, "Kali Linux Tools", "Weeks 13-14", "Get hands on with security tools.")
        dao.insertWeek(w3)
        dao.insertTask(TaskEntity(weekId = 3, description = "Scan network with Nmap"))
        dao.insertTask(TaskEntity(weekId = 3, description = "Capture packets with Wireshark"))
    }
}
