package com.ankama.cube.test.rest.Impl

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import akka.http.scaladsl.marshalling.ToEntityMarshaller
import akka.http.scaladsl.unmarshalling.FromEntityUnmarshaller
import com.ankama.cube.test.rest.api.{AdminsApiMarshaller, DevelopersApiMarshaller}
import com.ankama.cube.test.rest.model._
import com.github.plokhotnyuk.jsoniter_scala.core.{JsonCodec, JsonReader, JsonValueCodec, JsonWriter}
import com.github.plokhotnyuk.jsoniter_scala.macros.{CodecMakerConfig, JsonCodecMaker}

class Marshaller extends DevelopersApiMarshaller with AdminsApiMarshaller {
  val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")

  import de.heikoseeberger.akkahttpjsoniterscala.JsoniterScalaSupport._

  private implicit val dateCodec: JsonCodec[LocalDateTime] = new JsonCodec[LocalDateTime] {

    override def decodeValue(in: JsonReader, default: LocalDateTime): LocalDateTime ={
      val stringDate = in.readString("")
      LocalDateTime.parse(stringDate, dateTimeFormatter)
    }

    override def encodeValue(x: LocalDateTime, out: JsonWriter): Unit = out.writeVal(x.toString)

    override def nullValue: LocalDateTime = LocalDateTime.now()

    override def decodeKey(in: JsonReader): LocalDateTime = LocalDateTime.parse(in.readKeyAsString())

    override def encodeKey(x: LocalDateTime, out: JsonWriter): Unit = out.writeVal(x.toString)
  }

//  private implicit val manufacturerCodec: JsonValueCodec[Manufacturer] = JsonCodecMaker.make(CodecMakerConfig())
  private implicit val inventoryItemCodec: JsonValueCodec[InventoryItem] = JsonCodecMaker.make(CodecMakerConfig())
  private implicit val inventoryItemListCodec: JsonValueCodec[List[InventoryItem]] = JsonCodecMaker.make(CodecMakerConfig())

  override implicit def fromRequestUnmarshallerInventoryItem: FromEntityUnmarshaller[InventoryItem] = unmarshaller(inventoryItemCodec)

  override implicit def toEntityMarshallerList: ToEntityMarshaller[List[InventoryItem]] = marshaller

}
