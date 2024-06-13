package com.example.a6112_final_project_kotlin

import android.os.Parcel
import android.os.Parcelable

class Item(
    var id: Int,
    var name: String,
    var description: String,
    var category: String,
    var currQuantity: Int,
    var required: Int,
    var lowStock: Int,
    var price: Int // in cents
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(category)
        parcel.writeInt(currQuantity)
        parcel.writeInt(required)
        parcel.writeInt(lowStock)
        parcel.writeInt(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Item> {
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}