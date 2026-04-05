package com.aayar94.qrscanner.presentation.generate

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aayar94.qrscanner.R
import com.aayar94.qrscanner.core.component.PageHeader
import com.aayar94.qrscanner.core.theme.Gray
import com.aayar94.qrscanner.core.theme.QRScannerTheme
import com.aayar94.qrscanner.core.theme.Yellow
import com.aayar94.qrscanner.domain.model.QRCategory
import java.net.URLEncoder


@Composable
fun GenerateScreenContainer(
    categoryId: Int? = null,
    navigateBack: () -> Unit,
    onSaveQR: (Bitmap, QRCategory, String) -> Unit
) {
    val vm: GenerateQRViewModel = hiltViewModel()
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val uiEffect by vm.uiEffect.collectAsStateWithLifecycle(null)
    val uiAction = vm::onAction

    LaunchedEffect(categoryId) {
        categoryId?.let {
            uiAction.invoke(
                GenerateQRContact.UiAction.CategoryPickedInitalizeUI(
                    it
                )
            )
        }
    }

    LaunchedEffect(uiEffect) {
        when (uiEffect) {
            null -> {}
            GenerateQRContact.UiEffect.OnNavigateBack -> {
                navigateBack.invoke()
            }

            is GenerateQRContact.UiEffect.OnNavigateGeneratedQRDetail -> {
                onSaveQR.invoke(
                    (uiEffect as GenerateQRContact.UiEffect.OnNavigateGeneratedQRDetail).qrCode,
                    (uiEffect as GenerateQRContact.UiEffect.OnNavigateGeneratedQRDetail).category,
                    (uiEffect as GenerateQRContact.UiEffect.OnNavigateGeneratedQRDetail).qrProxy
                )
            }
        }
    }

    GenerateScreen(uiState, uiEffect, uiAction)
}

@Composable
private fun GenerateScreen(
    uiState: GenerateQRContact.UiState,
    uiEffect: GenerateQRContact.UiEffect?,
    uiAction: (GenerateQRContact.UiAction) -> Unit
) {
    val context = LocalContext.current
    Box(
        Modifier
            .fillMaxSize()
            .background(Gray)
            .consumeWindowInsets(WindowInsets.ime)
            .padding(WindowInsets.ime.asPaddingValues())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(36.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                PageHeader(
                    modifier = Modifier.fillMaxWidth(),
                    onLeftAction = { uiAction.invoke(GenerateQRContact.UiAction.OnBackPressed) },
                    leftActionIcon = Icons.AutoMirrored.Outlined.ArrowBack,
                    leftActionDescription = "Back",
                    title = uiState.selectedCategory?.name?.let { stringResource(it) }
                        ?: "Generate",
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .wrapContentHeight()
                        .background(Color.Black.copy(0.7f), shape = RoundedCornerShape(12.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = categoryLabel(uiState.selectedCategory?.id),
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.White
                        )
                        Spacer(Modifier.height(8.dp))

                        when (uiState.selectedCategory?.id) {
                            1 -> TextForm(uiAction)
                            2 -> WebsiteForm(uiAction)
                            3 -> WifiForm(uiAction)
                            4 -> CalendarForm(uiAction)
                            5 -> ContactsForm(uiAction)
                            6 -> OrganizationForm(uiAction)
                            7 -> LocationForm(uiAction)
                            8 -> WhatsAppForm(uiAction)
                            9 -> MailForm(uiAction)
                            10 -> InstagramForm(uiAction)
                            11 -> PhoneForm(uiAction)
                            else -> {}
                        }

                        Spacer(Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .background(Yellow, RoundedCornerShape(4.dp))
                                .clickable {
                                    uiAction.invoke(
                                        GenerateQRContact.UiAction.OnGeneraQrCode(
                                            qrProxy = uiState.uriText.orEmpty(),
                                            category = uiState.selectedCategory!!,
                                            bgColor = ContextCompat.getColor(
                                                context, R.color.white
                                            ),
                                            fgColor = ContextCompat.getColor(
                                                context, R.color.black
                                            )
                                        )
                                    )
                                }, contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "Generate QR Code",
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun categoryLabel(categoryId: Int?): String = when (categoryId) {
    1 -> "Enter your text"
    2 -> "Enter website URL"
    3 -> "Enter Wi-Fi details"
    4 -> "Enter event details"
    5 -> "Enter contact details"
    6 -> "Enter organization details"
    7 -> "Enter location coordinates"
    8 -> "Enter WhatsApp details"
    9 -> "Enter email details"
    10 -> "Enter Instagram username"
    11 -> "Enter phone number"
    else -> "Enter details"
}

// ── 1. Text ──────────────────────────────────────────────────────────────────
@Composable
private fun TextForm(uiAction: (GenerateQRContact.UiAction) -> Unit) {
    GenerateQRTextField(
        modifier = Modifier.fillMaxWidth(),
        title = "Text",
        isPasteEnabled = true,
        afterTextChanged = { uiAction(GenerateQRContact.UiAction.OnPasteClicked(it)) }
    )
}

// ── 2. Website ───────────────────────────────────────────────────────────────
@Composable
private fun WebsiteForm(uiAction: (GenerateQRContact.UiAction) -> Unit) {
    GenerateQRTextField(
        modifier = Modifier.fillMaxWidth(),
        title = "URL (e.g. https://example.com)",
        isPasteEnabled = true,
        afterTextChanged = { uiAction(GenerateQRContact.UiAction.OnPasteClicked(it)) }
    )
}

// ── 3. Wi-Fi ─────────────────────────────────────────────────────────────────
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WifiForm(uiAction: (GenerateQRContact.UiAction) -> Unit) {
    var ssid by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var encryption by remember { mutableStateOf("WPA") }
    var expanded by remember { mutableStateOf(false) }
    val encryptionOptions = listOf("WPA", "WEP", "nopass")

    fun buildString() {
        val enc = if (encryption == "nopass") "nopass" else encryption
        val qr = "WIFI:T:$enc;S:${ssid.escapeWifi()};P:${password.escapeWifi()};;"
        uiAction(GenerateQRContact.UiAction.OnPasteClicked(qr))
    }

    GenerateQRTextField(
        modifier = Modifier.fillMaxWidth(),
        title = "Network Name (SSID)",
        isPasteEnabled = false,
        afterTextChanged = { ssid = it; buildString() }
    )
    GenerateQRTextField(
        modifier = Modifier.fillMaxWidth(),
        title = "Password",
        isPasteEnabled = false,
        afterTextChanged = { password = it; buildString() }
    )

    Spacer(Modifier.height(4.dp))
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        TextField(
            value = encryption,
            onValueChange = {},
            readOnly = true,
            label = { Text("Encryption") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                focusedContainerColor = Color.Black,
                unfocusedTextColor = Color.White,
                unfocusedContainerColor = Color.Black,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            encryptionOptions.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        encryption = option
                        expanded = false
                        buildString()
                    }
                )
            }
        }
    }
}

private fun String.escapeWifi(): String =
    replace("\\", "\\\\").replace(";", "\\;").replace(",", "\\,").replace("\"", "\\\"")

// ── 4. Calendar ───────────────────────────────────────────────────────────────
@Composable
private fun CalendarForm(uiAction: (GenerateQRContact.UiAction) -> Unit) {
    var title by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    fun buildString() {
        val start = formatDateTime(startDate, startTime)
        val end = formatDateTime(endDate, endTime)
        val qr = buildString {
            append("BEGIN:VCALENDAR\nVERSION:2.0\nBEGIN:VEVENT\n")
            if (title.isNotBlank()) append("SUMMARY:$title\n")
            if (start.isNotBlank()) append("DTSTART:$start\n")
            if (end.isNotBlank()) append("DTEND:$end\n")
            if (location.isNotBlank()) append("LOCATION:$location\n")
            if (description.isNotBlank()) append("DESCRIPTION:$description\n")
            append("END:VEVENT\nEND:VCALENDAR")
        }
        uiAction(GenerateQRContact.UiAction.OnPasteClicked(qr))
    }

    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Event Title", afterTextChanged = { title = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Start Date (YYYYMMDD)", afterTextChanged = { startDate = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Start Time (HHmmss)", afterTextChanged = { startTime = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "End Date (YYYYMMDD)", afterTextChanged = { endDate = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "End Time (HHmmss)", afterTextChanged = { endTime = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Location (optional)", afterTextChanged = { location = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Description (optional)", afterTextChanged = { description = it; buildString() })
}

private fun formatDateTime(date: String, time: String): String {
    val d = date.filter { it.isDigit() }.take(8)
    val t = time.filter { it.isDigit() }.take(6)
    return if (d.length == 8 && t.length == 6) "${d}T${t}Z"
    else if (d.length == 8) "${d}T000000Z"
    else ""
}

// ── 5. Contacts ───────────────────────────────────────────────────────────────
@Composable
private fun ContactsForm(uiAction: (GenerateQRContact.UiAction) -> Unit) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    fun buildString() {
        val fullName = listOf(lastName, firstName).filter { it.isNotBlank() }.joinToString(";")
        val qr = buildString {
            append("BEGIN:VCARD\nVERSION:3.0\n")
            if (fullName.isNotBlank()) append("N:$fullName\n")
            if (firstName.isNotBlank() || lastName.isNotBlank())
                append("FN:${listOf(firstName, lastName).filter { it.isNotBlank() }.joinToString(" ")}\n")
            if (phone.isNotBlank()) append("TEL:$phone\n")
            if (email.isNotBlank()) append("EMAIL:$email\n")
            if (address.isNotBlank()) append("ADR:;;$address\n")
            append("END:VCARD")
        }
        uiAction(GenerateQRContact.UiAction.OnPasteClicked(qr))
    }

    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "First Name", afterTextChanged = { firstName = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Last Name", afterTextChanged = { lastName = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Phone", afterTextChanged = { phone = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Email", afterTextChanged = { email = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Address (optional)", afterTextChanged = { address = it; buildString() })
}

// ── 6. Organization ───────────────────────────────────────────────────────────
@Composable
private fun OrganizationForm(uiAction: (GenerateQRContact.UiAction) -> Unit) {
    var orgName by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var website by remember { mutableStateOf("") }

    fun buildString() {
        val qr = buildString {
            append("BEGIN:VCARD\nVERSION:3.0\n")
            if (orgName.isNotBlank()) append("ORG:$orgName\n")
            if (title.isNotBlank()) append("TITLE:$title\n")
            if (phone.isNotBlank()) append("TEL:$phone\n")
            if (email.isNotBlank()) append("EMAIL:$email\n")
            if (website.isNotBlank()) append("URL:$website\n")
            append("END:VCARD")
        }
        uiAction(GenerateQRContact.UiAction.OnPasteClicked(qr))
    }

    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Organization Name", afterTextChanged = { orgName = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Job Title / Role (optional)", afterTextChanged = { title = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Phone (optional)", afterTextChanged = { phone = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Email (optional)", afterTextChanged = { email = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Website (optional)", afterTextChanged = { website = it; buildString() })
}

// ── 7. Location ───────────────────────────────────────────────────────────────
@Composable
private fun LocationForm(uiAction: (GenerateQRContact.UiAction) -> Unit) {
    var lat by remember { mutableStateOf("") }
    var lng by remember { mutableStateOf("") }
    var query by remember { mutableStateOf("") }

    fun buildString() {
        val qr = if (lat.isNotBlank() && lng.isNotBlank()) {
            if (query.isNotBlank()) "geo:$lat,$lng?q=${URLEncoder.encode(query, "UTF-8")}"
            else "geo:$lat,$lng"
        } else ""
        uiAction(GenerateQRContact.UiAction.OnPasteClicked(qr))
    }

    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Latitude (e.g. 37.7749)", afterTextChanged = { lat = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Longitude (e.g. -122.4194)", afterTextChanged = { lng = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Label (optional)", afterTextChanged = { query = it; buildString() })
}

// ── 8. WhatsApp ───────────────────────────────────────────────────────────────
@Composable
private fun WhatsAppForm(uiAction: (GenerateQRContact.UiAction) -> Unit) {
    var phone by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    fun buildString() {
        val cleanPhone = phone.filter { it.isDigit() || it == '+' }
        val qr = if (message.isNotBlank())
            "https://wa.me/$cleanPhone?text=${URLEncoder.encode(message, "UTF-8")}"
        else
            "https://wa.me/$cleanPhone"
        uiAction(GenerateQRContact.UiAction.OnPasteClicked(qr))
    }

    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Phone Number (with country code)", afterTextChanged = { phone = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Pre-filled Message (optional)", afterTextChanged = { message = it; buildString() })
}

// ── 9. Mail ───────────────────────────────────────────────────────────────────
@Composable
private fun MailForm(uiAction: (GenerateQRContact.UiAction) -> Unit) {
    var to by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    fun buildString() {
        val params = buildList {
            if (subject.isNotBlank()) add("subject=${URLEncoder.encode(subject, "UTF-8")}")
            if (body.isNotBlank()) add("body=${URLEncoder.encode(body, "UTF-8")}")
        }.joinToString("&")
        val qr = if (params.isNotBlank()) "mailto:$to?$params" else "mailto:$to"
        uiAction(GenerateQRContact.UiAction.OnPasteClicked(qr))
    }

    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "To (email address)", isPasteEnabled = true, afterTextChanged = { to = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Subject (optional)", afterTextChanged = { subject = it; buildString() })
    GenerateQRTextField(modifier = Modifier.fillMaxWidth(), title = "Body (optional)", afterTextChanged = { body = it; buildString() })
}

// ── 10. Instagram ─────────────────────────────────────────────────────────────
@Composable
private fun InstagramForm(uiAction: (GenerateQRContact.UiAction) -> Unit) {
    GenerateQRTextField(
        modifier = Modifier.fillMaxWidth(),
        title = "Username (without @)",
        isPasteEnabled = false,
        afterTextChanged = {
            val username = it.trimStart('@')
            uiAction(GenerateQRContact.UiAction.OnPasteClicked("https://instagram.com/$username"))
        }
    )
}

// ── 11. Phone ─────────────────────────────────────────────────────────────────
@Composable
private fun PhoneForm(uiAction: (GenerateQRContact.UiAction) -> Unit) {
    GenerateQRTextField(
        modifier = Modifier.fillMaxWidth(),
        title = "Phone Number",
        isPasteEnabled = true,
        afterTextChanged = { uiAction(GenerateQRContact.UiAction.OnPasteClicked("tel:$it")) }
    )
}

@Preview
@Composable
private fun GenerateScreenPreview() {
    QRScannerTheme {
        GenerateScreen(
            uiState = GenerateQRContact.UiState(
                selectedCategory = QRCategory(2, R.string.category_website, R.drawable.ic_internet)
            ), uiEffect = null, uiAction = {})
    }
}
