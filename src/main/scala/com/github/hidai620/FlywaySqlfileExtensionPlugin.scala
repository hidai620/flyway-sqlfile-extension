package com.github.hidai620

import sbt._
import Keys._
import java.text.SimpleDateFormat
import java.io.File
import java.util.Date
import org.flywaydb.sbt.FlywayPlugin._

object FlywaySqlfileExtension extends Plugin {

  val flywayAddSqlFile = inputKey[Unit]("flywayのSQLファイルを作成する")

  val flywaySqlFileTimeFormat = settingKey[String]("flyway migration sql file name timestamp formt")

  override val settings = Seq(
    flywaySqlFileTimeFormat := "yyyy.MM.dd.HH.mm.ss",

    flywayAddSqlFile := {
      val args: Seq[String] = Def.spaceDelimited("<arg>").parsed
      val formatter = new SimpleDateFormat(flywaySqlFileTimeFormat.value)
      val sqlFileName = s"${flywaySqlMigrationPrefix.value}${formatter.format(new Date)}${flywaySqlMigrationSeparator.value}${args.mkString("_")}${flywaySqlMigrationSuffix.value}"
      val migrateSqlFileDirName = "src/main/resources/db/migration"
      val sqlFilePath = s"${baseDirectory.value}/${migrateSqlFileDirName}/${sqlFileName}"
      new File(sqlFilePath).createNewFile
    }
  )

}
