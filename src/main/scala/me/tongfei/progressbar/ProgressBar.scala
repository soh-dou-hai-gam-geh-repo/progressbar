package me.tongfei.progressbar

import com.github.nscala_time.time.Imports._
import com.github.nscala_time.time.Implicits._

/**
 * A simple console-based progress bar.
 *
 * @param task Name of the progress bar
 * @param max Number of steps when the task is complete.
 * @param length The length of the progress bar shown in console. Default value is 50 characters.
 * @author Tongfei Chen (ctongfei@gmail.com).
 */
class ProgressBar(val task: String, val max: Int, val length: Int = 50) {

  private[this] var current = 0
  private[this] var startTime: DateTime = null
  private[this] var started = false

  private[this] def repeat(x: Char, n: Int): String = {
    new String(Array.fill[Char](n)(x))
  }

  private[this] def progress = math.round(current.toDouble / max * length).toInt

  private[this] def eta(elapsed: Duration) = {
    if (progress == 0) "?"
    else elapsed.dividedBy(progress).multipliedBy(length - progress).toString
  }

  private[this] def show() = {
    if (!started) {
      println()
      started = true
    }
    print('\r')
    val elapsed = new Duration(startTime, DateTime.now)
    print(task + " [" + repeat('=', progress) + repeat(' ', length - progress) + "] " + math.round(current.toDouble / max * 100) + "% " +
      "(Elapsed: " + elapsed.toString + " Remaining: " + eta(elapsed) + ")")
  }

  /**
   * Starts the progress bar.
   */
  def start() = {
    startTime = DateTime.now
    show()
  }

  /**
   * Advances the progress bar by a specific amount.
   * @param n Steps
   */
  def stepBy(n: Int) = {
    current += n
    show()
  }

  /**
   * Advances the progress bar by one step.
   */
  def step() = {
    current += 1
    show()
  }

  /**
   * Stops the progress bar.
   */
  def stop() = {
    show()
    println()
  }


}