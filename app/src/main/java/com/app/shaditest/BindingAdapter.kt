package com.app.shaditest

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.app.shaditest.model.Location
import com.app.shaditest.model.Name
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat


@BindingAdapter("name")
fun setName(view: TextView, name: Name) {
    view.text = "${name.title.capitalize()} ${name.first.capitalize()} ${name.last.capitalize()}"
}

@BindingAdapter("imageUrl")
fun loadImageUrl(view: ImageView, url: String) {
    Picasso.get().load(url)
            .transform(CircleTransform())
            .into(view)
}

@BindingAdapter("age")
fun setUserDetails(view: TextView, age: Any) {
    view.text = "$age yrs"
}

@BindingAdapter("address")
fun setAddress(view: TextView, location: Location) {
    view.text = "${location.street}, ${location.city}, ${location.state}, ${location.postcode}"
}

@BindingAdapter("date")
fun setDate(view: TextView, date: String) {
    val registerDate = date.split("T")[0]
    val displayDate = SimpleDateFormat("dd MMM, yyyy")
            .format(SimpleDateFormat("yyyy-MM-dd").parse(registerDate))
    view.text = displayDate
}