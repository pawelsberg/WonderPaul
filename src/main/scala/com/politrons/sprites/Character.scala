package com.politrons.sprites

import com.politrons.engine.ThunderboltEngine
import com.politrons.sprites.SpriteUtils.{changeImageIcon, scaleImage}

import java.awt.Image
import java.awt.event.KeyEvent
import javax.swing.ImageIcon

class Character(thunderboltEngine: ThunderboltEngine,
                var x: Integer = 540,
                var y: Integer = 78) {

  private var frame = 0
  private var dx = 0
  private var dy = 0
  var orientation = ""
  var image: Image = _
  var imageIcon: ImageIcon = _

  val images = Map(
    "left-" + 1 -> new ImageIcon("src/main/resources/character/pirate-left-1.png"),
    "left-" + 2 -> new ImageIcon("src/main/resources/character/pirate-left-2.png"),
    "right-" + 1 -> new ImageIcon("src/main/resources/character/pirate-right-1.png"),
    "right-" + 2 -> new ImageIcon("src/main/resources/character/pirate-right-2.png"),
    "up-" + 1 -> new ImageIcon("src/main/resources/character/pirate-up-1.png"),
    "up-" + 2 -> new ImageIcon("src/main/resources/character/pirate-up-2.png"),
    "down-" + 1 -> new ImageIcon("src/main/resources/character/pirate-down-1.png"),
    "down-" + 2 -> new ImageIcon("src/main/resources/character/pirate-down-2.png")
  )

  loadImage()

  private def loadImage(): Unit = {
    imageIcon = images("left-1")
    image = imageIcon.getImage
    image = scaleImage(image, 40, 40)
    imageIcon = new ImageIcon(image)
  }

  val mapCollision = List(
    Tuple2(320, Tuple2(360, 260))
  )

  def move(): Unit = {
    if (!isAreaAvailable) {
      x += dx
      y += dy
    }
  }

  private def isAreaAvailable: Boolean = {
    val tmpX = x + dx
    val tmpY = y + dy
     mapCollision.exists(tuple => {
      val colX = tuple._1;
      val tupleY = tuple._2
      return Math.abs(colX - tmpX) <= 10 && (tmpY <= tupleY._1) && (tmpY >= tupleY._2)
    })
  }

  def keyPressed(e: KeyEvent): Unit = {
    e.getKeyCode match {
      case KeyEvent.VK_LEFT =>
        dx = -2
        orientation = "left"
        imageIcon = changeImageIcon(images(s"$orientation-" + increaseFrame))
      case KeyEvent.VK_RIGHT =>
        dx = 2
        orientation = "right"
        imageIcon = changeImageIcon(images(s"$orientation-" + increaseFrame))
      case KeyEvent.VK_UP =>
        dy = -2
        orientation = "up"
        imageIcon = changeImageIcon(images(s"$orientation-" + increaseFrame))
      case KeyEvent.VK_DOWN =>
        dy = 2
        orientation = "down"
        imageIcon = changeImageIcon(images(s"$orientation-" + increaseFrame))
      case KeyEvent.VK_SPACE =>
        thunderboltEngine.directionOfThunderbolt(orientation, x, y)
      case _ => println(s"Key not implemented")
    }
  }

  private def increaseFrame = {
    if (frame == 2) frame = 1
    else frame += 1
    frame
  }

  def keyReleased(e: KeyEvent): Unit = {
    e.getKeyCode match {
      case KeyEvent.VK_LEFT => dx = 0
      case KeyEvent.VK_RIGHT => dx = 0
      case KeyEvent.VK_UP => dy = 0
      case KeyEvent.VK_DOWN => dy = 0
      case KeyEvent.VK_SPACE => ()
    }
  }
}