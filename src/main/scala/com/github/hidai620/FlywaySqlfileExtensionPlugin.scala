package com.github.hidai620

import sbt._
import Keys._
import java.io.File
import java.util.Date
import java.text.SimpleDateFormat
import org.flywaydb.sbt.FlywayPlugin._

object FlywaySqlFileExtension extends sbt.AutoPlugin {

  object autoImport {
    val flywaySqlFileAdd = inputKey[Unit]("Add flyway versioned sql file")
    val flywaySqlFileTimeFormat = settingKey[String]("flyway migration sql file name timestamp format")
  }

  val migrationSqlFileDirName = "src/main/resources/db/migration"


  import autoImport._
  override def projectSettings:Seq[Def.Setting[_]] = Seq(
    flywaySqlFileTimeFormat := "yyyy.MM.dd.HH.mm.ss",
    flywaySqlFileAdd := {
      val args: Seq[String] = Def.spaceDelimited("<arg>").parsed
      val timestamp = new SimpleDateFormat(flywaySqlFileTimeFormat.value).format(new Date)
      val description = args.mkString("_")

      val sqlFileName = s"${flywaySqlMigrationPrefix.value}${timestamp}${flywaySqlMigrationSeparator.value}"+
        s"${description}${flywaySqlMigrationSuffix.value}"

      val sqlFileDirPath = s"${baseDirectory.value}/${migrationSqlFileDirName}"

      createSqlFileDirectory(sqlFileDirPath)
      createSqlFile(sqlFileDirPath, sqlFileName)
    }
  )

  private def createSqlFile(sqlFileDirPath:String, sqlFileName:String):Boolean = {
    val sqlFilePath = s"${sqlFileDirPath}/${sqlFileName}"
    new File(sqlFilePath).createNewFile
  }

  private def createSqlFileDirectory(sqlFileDirPath:String):Boolean = {
    val sqlFileDir = new File(sqlFileDirPath)
    if (!sqlFileDir.exists) {
      sqlFileDir.mkdirs
    } else {
      true
    }
  }

  override def trigger = allRequirements
}
