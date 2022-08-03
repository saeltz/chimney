package io.scalaland.chimney.benchmarks

import org.openjdk.jmh.annotations.{Setup, Benchmark, Level}
import io.scalaland.chimney.dsl._
import io.scalaland.chimney.Transformer
import scala.annotation.switch

class CoproductTransformBenchmark extends CommonBenchmarkSettings {

  import fixtures._

  private val color2ChannelT = Transformer.derive[Color, Channel]
  private val channel2ColorT = Transformer
    .define[Channel, Color]
    .withCoproductInstance { (_: Channel.Alpha.type) =>
      Color.Blue
    }
    .buildTransformer

  var color: Color = Color.Red

  @Setup(Level.Iteration)
  def nextColor(): Unit = {
    color = color match {
      case Color.Red   => Color.Green
      case Color.Green => Color.Blue
      case Color.Blue  => Color.Red
    }
  }

  @Benchmark
  def coproductIsomorphismChimneyWithDsl: Color =
    color
      .transformInto[Channel]
      .into[Color]
      .withCoproductInstance { (_: Channel.Alpha.type) =>
        Color.Blue
      }
      .transform

  @Benchmark
  def coproductIsomorphismChimney: Color = channel2ColorT.transform(color2ChannelT.transform(color))

  @Benchmark
  def coproductIsomorphismByHand: Color = channel2Color(color2Channel(color))

}
