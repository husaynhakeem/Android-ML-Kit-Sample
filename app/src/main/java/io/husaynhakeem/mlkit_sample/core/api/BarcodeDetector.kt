package io.husaynhakeem.mlkit_sample.core.api

import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode.*
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import io.husaynhakeem.mlkit_sample.core.visionimage.BitmapVisionImageGenerator


class BarcodeDetector : MLKitApi<FirebaseVisionBarcodeDetector, List<FirebaseVisionBarcode>>() {

    override val processor: FirebaseVisionBarcodeDetector
        get() = firebaseVisionInstance.visionBarcodeDetector

    override fun detectInImage(image: String, onSuccess: (String) -> Unit, onFailure: (String) -> Unit): Task<List<FirebaseVisionBarcode>> {
        return processor.detectInImage(BitmapVisionImageGenerator(image).get())
    }

    override fun onDetectionSuccess(result: List<FirebaseVisionBarcode>) = with(StringBuilder()) {
        result.forEach {
            append("Barcode raw value: ${it.rawValue}\n")
            append("Barcode display value: ${it.displayValue}\n")
            append("Barcode format: ${it.format}\n")

            when (it.valueType) {
                TYPE_WIFI -> append(onBarcodeOfTypeWifi(it.wifi))
                TYPE_CALENDAR_EVENT -> append(onBarcodeOfTypeCalendarEvent(it.calendarEvent))
                TYPE_CONTACT_INFO -> append(onBarcodeOfTypeContactInfo(it.contactInfo))
                TYPE_DRIVER_LICENSE -> append(onBarcodeOfTypeDriverLicense(it.driverLicense))
                TYPE_EMAIL -> append(onBarcodeOfTypeEmail(it.email))
                TYPE_GEO -> append(onBarcodeOfTypeGeoPoint(it.geoPoint))
                TYPE_PHONE -> append(onBarcodeOfTypePhone(it.phone))
                TYPE_SMS -> append(onBarcodeOfTypeSms(it.sms))
                TYPE_URL -> append(onBarcodeOfTypeUrl(it.url))
                TYPE_TEXT -> append("Barcode of type text")
                TYPE_PRODUCT -> append("Barcode of type product")
                TYPE_ISBN -> append("Barcode of type isbn")
                TYPE_UNKNOWN -> append("Barcode of unknown type")
            }
        }

        if (this.isBlank()) {
            return RESULT_TITLE + EMPTY_RESULT_MESSAGE
        }

        RESULT_TITLE + toString()
    }

    private fun onBarcodeOfTypeWifi(wifi: WiFi?): String = with(StringBuilder()) {
        append("Barcode of type wifi\n")
        wifi?.let {
            append("Password: ${it.password}\n")
            append("Encryption type: ${it.encryptionType}\n")
            append("Ssid: ${it.ssid}\n")
        }
        toString()
    }

    private fun onBarcodeOfTypeCalendarEvent(calendarEvent: CalendarEvent?): String = with(StringBuilder()) {
        append("Barcode of type calendar event\n")
        calendarEvent?.let {
            append("Organizer: ${it.organizer}\n")
            append("Summary: ${it.summary}\n")
            append("Location: ${it.location}\n")
            append("Description: ${it.description}\n")
            append("Status: ${it.status}\n")
            append("Starts ${it.start} and ends ${it.end}\n")
        }
        toString()
    }

    private fun onBarcodeOfTypeContactInfo(contactInfo: ContactInfo?): String = with(StringBuilder()) {
        append("Barcode of type contact info\n")
        contactInfo?.let {
            append("Name: ${it.name}\n")
            append("Title: ${it.title}\n")
            append("Organization: ${it.organization}\n")
            append("Emails: ${it.emails}\n")
            append("Addresses: ${it.addresses}\n")
            append("Phones: ${it.phones}\n")
            append("Urls: ${it.urls}\n")
        }
        toString()
    }

    private fun onBarcodeOfTypeDriverLicense(driverLicense: DriverLicense?): String = with(StringBuilder()) {
        append("Barcode of type driver license\n")
        driverLicense?.let {
            append("Full name: ${it.firstName} ${it.middleName} ${it.lastName}\n")
            append("Gender: ${it.gender}\n")
            append("Born: ${it.birthDate}\n")
            append("Issued: ${it.issueDate}, expires ${it.expiryDate}\n")
            append("Address: ${it.addressStreet}, ${it.addressCity}, ${it.addressState}, ${it.addressZip}\n")
        }
        toString()
    }

    private fun onBarcodeOfTypeEmail(email: Email?): String = with(StringBuilder()) {
        append("Barcode of type email\n")
        email?.let {
            append("Addressed to ${it.address}\n")
            append("Subject: ${it.subject}\n")
            append("Type: ${it.type}\n")
            append("Body: ${it.body}\n")
        }
        toString()
    }

    private fun onBarcodeOfTypeGeoPoint(geoPoint: GeoPoint?): String = with(StringBuilder()) {
        append("Barcode of type geoPoint\n")
        geoPoint?.let {
            append("Coordinates(${it.lat}, ${it.lng})\n")
        }
        toString()
    }

    private fun onBarcodeOfTypePhone(phone: Phone?): String = with(StringBuilder()) {
        append("Barcode of type phone\n")
        phone?.let {
            append("Type: ${it.type}\n")
            append("Number: ${it.number}\n")
        }
        toString()
    }

    private fun onBarcodeOfTypeSms(sms: Sms?): String = with(StringBuilder()) {
        append("Barcode of type sms\n")
        sms?.let {
            append("Phone number: ${it.phoneNumber}\n")
            append("Message: ${it.message}\n")
        }
        toString()
    }

    private fun onBarcodeOfTypeUrl(url: UrlBookmark?): String = with(StringBuilder()) {
        append("Barcode of type url\n")
        url?.let {
            append("Title: ${it.title}\n")
            append("Url: ${it.url}\n")
        }
        toString()
    }

    override fun onDetectionFailure(exception: Exception): String {
        return ERROR_MESSAGE + exception.message
    }

    companion object {
        private const val RESULT_TITLE = "Barcode detection results\n\n"

        private const val EMPTY_RESULT_MESSAGE = "Failed to detect barcodes in the provided image."

        private const val ERROR_MESSAGE = "An error occurred while trying to detect barcodes in the provided image.\n\nCause: "
    }
}