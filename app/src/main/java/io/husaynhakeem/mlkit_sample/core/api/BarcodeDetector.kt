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
            append("Barcode raw value: ${it.rawValue}")
            append("Barcode display value: ${it.displayValue}")
            append("Barcode format: ${it.format}")

            when (it.valueType) {
                TYPE_WIFI -> onBarcodeOfTypeWifi(this, it.wifi)
                TYPE_CALENDAR_EVENT -> onBarcodeOfTypeCalendarEvent(this, it.calendarEvent)
                TYPE_CONTACT_INFO -> onBarcodeOfTypeContactInfo(this, it.contactInfo)
                TYPE_DRIVER_LICENSE -> onBarcodeOfTypeDriverLicense(this, it.driverLicense)
                TYPE_EMAIL -> onBarcodeOfTypeEmail(this, it.email)
                TYPE_GEO -> onBarcodeOfTypeGeoPoint(this, it.geoPoint)
                TYPE_PHONE -> onBarcodeOfTypePhone(this, it.phone)
                TYPE_SMS -> onBarcodeOfTypeSms(this, it.sms)
                TYPE_URL -> onBarcodeOfTypeUrl(this, it.url)
                TYPE_TEXT -> append("Barcode of type text")
                TYPE_PRODUCT -> append("Barcode of type product")
                TYPE_ISBN -> append("Barcode of type isbn")
                TYPE_UNKNOWN -> append("Barcode of unknown type")
            }
        }
        toString()
    }

    private fun onBarcodeOfTypeWifi(sb: StringBuilder, wifi: WiFi?) {
        sb.append("Barcode of type wifi")
        wifi?.let {
            sb.append("Password: ${it.password}, encryption type: ${it.encryptionType}, ssid: ${it.ssid}")
        }
    }

    private fun onBarcodeOfTypeCalendarEvent(sb: StringBuilder, calendarEvent: CalendarEvent?) {
        sb.append("Barcode of type calendar event")
        calendarEvent?.let {
            sb.append("Organizer: ${it.organizer}")
            sb.append("Summary: ${it.summary}")
            sb.append("Location: ${it.location}")
            sb.append("Description: ${it.description}")
            sb.append("Status: ${it.status}")
            sb.append("Starts ${it.start} and ends ${it.end}")
        }
    }

    private fun onBarcodeOfTypeContactInfo(sb: StringBuilder, contactInfo: ContactInfo?) {
        sb.append("Barcode of type contact info")
        contactInfo?.let {
            sb.append("Name: ${it.name}")
            sb.append("Title: ${it.title}")
            sb.append("Organization: ${it.organization}")
            sb.append("Emails: ${it.emails}")
            sb.append("Addresses: ${it.addresses}")
            sb.append("Phones: ${it.phones}")
            sb.append("Urls: ${it.urls}")
        }
    }

    private fun onBarcodeOfTypeDriverLicense(sb: StringBuilder, driverLicense: DriverLicense?) {
        sb.append("Barcode of type driver license")
        driverLicense?.let {
            sb.append("Full name: ${it.firstName} ${it.middleName} ${it.lastName}")
            sb.append("Gender: ${it.gender}")
            sb.append("Born: ${it.birthDate}")
            sb.append("Issued: ${it.issueDate}, expires ${it.expiryDate}")
            sb.append("Address: ${it.addressStreet}, ${it.addressCity}, ${it.addressState}, ${it.addressZip}")
        }
    }

    private fun onBarcodeOfTypeEmail(sb: StringBuilder, email: Email?) {
        sb.append("Barcode of type email")
        email?.let {
            sb.append("Addressed to ${it.address}")
            sb.append("Subject: ${it.subject}")
            sb.append("Type: ${it.type}")
            sb.append("Body: ${it.body}")
        }
    }

    private fun onBarcodeOfTypeGeoPoint(sb: StringBuilder, geoPoint: GeoPoint?) {
        sb.append("Barcode of type geoPoint")
        geoPoint?.let {
            sb.append("Coordinates(${it.lat}, ${it.lng})")
        }
    }

    private fun onBarcodeOfTypePhone(sb: StringBuilder, phone: Phone?) {
        sb.append("Barcode of type phone")
        phone?.let {
            sb.append("Type: ${it.type}")
            sb.append("Number: ${it.number}")
        }
    }

    private fun onBarcodeOfTypeSms(sb: StringBuilder, sms: Sms?) {
        sb.append("Barcode of type sms")
        sms?.let {
            sb.append("Phone number: ${it.phoneNumber}")
            sb.append("Message: ${it.message}")
        }
    }

    private fun onBarcodeOfTypeUrl(sb: StringBuilder, url: UrlBookmark?) {
        sb.append("Barcode of type url")
        url?.let {
            sb.append("Title: ${it.title}")
            sb.append("Url: ${it.url}")
        }
    }

    override fun onDetectionFailure(exception: Exception): String {
        return "Failed to read barcode\nCause: ${exception.message}"
    }
}