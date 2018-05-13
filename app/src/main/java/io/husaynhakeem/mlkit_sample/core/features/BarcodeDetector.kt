package io.husaynhakeem.mlkit_sample.core.features

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcode.*
import com.google.firebase.ml.vision.barcode.FirebaseVisionBarcodeDetector
import com.google.firebase.ml.vision.common.FirebaseVisionImage


class BarcodeDetector : MLKitFeature<FirebaseVisionBarcodeDetector, List<FirebaseVisionBarcode>>() {

    override val processor: FirebaseVisionBarcodeDetector
        get() = firebaseVisionInstance.visionBarcodeDetector

    override fun process(image: FirebaseVisionImage): Task<List<FirebaseVisionBarcode>> {
        return processor.detectInImage(image)
    }

    override fun onProcessSuccess(result: List<FirebaseVisionBarcode>) {
        result.forEach {
            Log.d(TAG, "Barcode raw value: ${it.rawValue}")
            Log.d(TAG, "Barcode display value: ${it.displayValue}")
            Log.d(TAG, "Barcode format: ${it.format}")

            when (it.valueType) {
                TYPE_WIFI -> onBarcodeOfTypeWifi(it.wifi)
                TYPE_CALENDAR_EVENT -> onBarcodeOfTypeCalendarEvent(it.calendarEvent)
                TYPE_CONTACT_INFO -> onBarcodeOfTypeContactInfo(it.contactInfo)
                TYPE_DRIVER_LICENSE -> onBarcodeOfTypeDriverLicense(it.driverLicense)
                TYPE_EMAIL -> onBarcodeOfTypeEmail(it.email)
                TYPE_GEO -> onBarcodeOfTypeGeoPoint(it.geoPoint)
                TYPE_PHONE -> onBarcodeOfTypePhone(it.phone)
                TYPE_SMS -> onBarcodeOfTypeSms(it.sms)
                TYPE_URL -> onBarcodeOfTypeUrl(it.url)
                TYPE_TEXT -> Log.d(TAG, "Barcode of type text")
                TYPE_PRODUCT -> Log.d(TAG, "Barcode of type product")
                TYPE_ISBN -> Log.d(TAG, "Barcode of type isbn")
                TYPE_UNKNOWN -> Log.d(TAG, "Barcode of unknown type")
            }
        }
    }

    private fun onBarcodeOfTypeWifi(wifi: WiFi?) {
        Log.d(TAG, "Barcode of type wifi")
        wifi?.let {
            Log.d(TAG, "Password: ${it.password}, encryption type: ${it.encryptionType}, ssid: ${it.ssid}")
        }
    }

    private fun onBarcodeOfTypeCalendarEvent(calendarEvent: CalendarEvent?) {
        Log.d(TAG, "Barcode of type calendar event")
        calendarEvent?.let {
            Log.d(TAG, "Organizer: ${it.organizer}")
            Log.d(TAG, "Summary: ${it.summary}")
            Log.d(TAG, "Location: ${it.location}")
            Log.d(TAG, "Description: ${it.description}")
            Log.d(TAG, "Status: ${it.status}")
            Log.d(TAG, "Starts ${it.start} and ends ${it.end}")
        }
    }

    private fun onBarcodeOfTypeContactInfo(contactInfo: ContactInfo?) {
        Log.d(TAG, "Barcode of type contact info")
        contactInfo?.let {
            Log.d(TAG, "Name: ${it.name}")
            Log.d(TAG, "Title: ${it.title}")
            Log.d(TAG, "Organization: ${it.organization}")
            Log.d(TAG, "Emails: ${it.emails}")
            Log.d(TAG, "Addresses: ${it.addresses}")
            Log.d(TAG, "Phones: ${it.phones}")
            Log.d(TAG, "Urls: ${it.urls}")
        }
    }

    private fun onBarcodeOfTypeDriverLicense(driverLicense: DriverLicense?) {
        Log.d(TAG, "Barcode of type driver license")
        driverLicense?.let {
            Log.d(TAG, "Full name: ${it.firstName} ${it.middleName} ${it.lastName}")
            Log.d(TAG, "Gender: ${it.gender}")
            Log.d(TAG, "Born: ${it.birthDate}")
            Log.d(TAG, "Issued: ${it.issueDate}, expires ${it.expiryDate}")
            Log.d(TAG, "Address: ${it.addressStreet}, ${it.addressCity}, ${it.addressState}, ${it.addressZip}")
        }
    }

    private fun onBarcodeOfTypeEmail(email: Email?) {
        Log.d(TAG, "Barcode of type email")
        email?.let {
            Log.d(TAG, "Addressed to ${it.address}")
            Log.d(TAG, "Subject: ${it.subject}")
            Log.d(TAG, "Type: ${it.type}")
            Log.d(TAG, "Body: ${it.body}")
        }
    }

    private fun onBarcodeOfTypeGeoPoint(geoPoint: GeoPoint?) {
        Log.d(TAG, "Barcode of type geoPoint")
        geoPoint?.let {
            Log.d(TAG, "Coordinates(${it.lat}, ${it.lng})")
        }
    }

    private fun onBarcodeOfTypePhone(phone: Phone?) {
        Log.d(TAG, "Barcode of type phone")
        phone?.let {
            Log.d(TAG, "Type: ${it.type}")
            Log.d(TAG, "Number: ${it.number}")
        }
    }

    private fun onBarcodeOfTypeSms(sms: Sms?) {
        Log.d(TAG, "Barcode of type sms")
        sms?.let {
            Log.d(TAG, "Phone number: ${it.phoneNumber}")
            Log.d(TAG, "Message: ${it.message}")
        }
    }

    private fun onBarcodeOfTypeUrl(url: UrlBookmark?) {
        Log.d(TAG, "Barcode of type url")
        url?.let {
            Log.d(TAG, "Title: ${it.title}")
            Log.d(TAG, "Url: ${it.url}")
        }
    }

    override fun onProcessFailure(exception: Exception) {
        Log.e(TAG, "Failed to read barcode\nCause: ${exception.message}")
    }

    companion object {
        private val TAG = BarcodeDetector::class.java.simpleName
    }
}