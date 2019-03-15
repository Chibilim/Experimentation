package com.ankama.cube.test.rest.model

import java.time.LocalDateTime
import java.util.UUID

/**
  * @param id   for example: ''d290f1ee-6c54-4b01-90e6-d701748f0851''
  * @param name for example: ''Widget Adapter''
  * @param releaseDate
  * @param manufacturer
  */
case class InventoryItem(
                          id: UUID,
                          name: String,
                          releaseDate: LocalDateTime,
                          manufacturer: Manufacturer
                        )

