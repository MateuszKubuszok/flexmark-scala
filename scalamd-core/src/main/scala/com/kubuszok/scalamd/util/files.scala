package com.kubuszok.scalamd.util

import java.io.{BufferedReader, File, FileInputStream, InputStreamReader, IOException}
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import scala.annotation.targetName
import scala.util.{Try, Using}

// FileUtil.java rewritten

extension (file: File)
  @targetName("plus")
  def /(name: String): File =
    new File(file, name)

  def isChildOf(another: File): Boolean =
    file.getAbsolutePath.startsWith(another.getAbsolutePath)

  def getNameOnly: String =
    val name = file.getName
    val pos = name.lastIndexOf('.')
    if pos > 0 && pos > name.lastIndexOf(File.separatorChar) then name.substring(0, pos) else name

  def getDotExtension: String =
    val name = file.getName
    val pos = name.lastIndexOf('.')
    if pos > 0 && pos > name.lastIndexOf(File.separatorChar) then name.substring(pos) else ""

  def pathSlash: String =
    val path = file.getPath
    val pos = path.lastIndexOf(File.separatorChar)
    if pos != -(1) then path.substring(0, pos + 1) else ""

  def getFileContentWithException: Either[IOException, String] = try {
    val sb = new StringBuilder
    Using.Manager { use =>
      val inputStream = use(new FileInputStream(file))
      val streamReader = use(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
      val reader = new BufferedReader(streamReader)
      var line: String = null
      while { line = reader.readLine; line != null } do sb.append(line).append("\n")
    }
    Right(sb.toString)
  } catch {
    case e: IOException => Left(e)
  }

  def getFileContent: Option[String] = getFileContentWithException match {
    case Left(e) =>
      e.printStackTrace()
      None
    case Right(str) => Some(str)
  }

  def getFileContentBytesWithException: Either[IOException, Array[Byte]] =
    try Right(Files.readAllBytes(file.toPath))
    catch { case e: IOException => Left(e) }

  def getFileContentBytes: Option[Array[Byte]] = getFileContentBytesWithException match
    case Left(e) =>
      e.printStackTrace()
      None
    case Right(arr) => Some(arr)
