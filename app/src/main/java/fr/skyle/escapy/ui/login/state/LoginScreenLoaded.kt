package fr.skyle.escapy.ui.login.state

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.ui.login.components.SignUpWithButton


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreenLoaded() {
    Column(
        modifier = Modifier.padding(12.dp, 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val loginState = remember { mutableStateOf(TextFieldValue()) }
        val passwordState = remember { mutableStateOf(TextFieldValue()) }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = loginState.value,
            onValueChange = { loginState.value = it },
            label = { Text("Identifiant") }
        )

        Spacer(modifier = Modifier.padding(8.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Mot de passe") }
        )

        Spacer(modifier = Modifier.padding(12.dp))

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Se connecter")
        }

        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier
                .padding(0.dp, 16.dp)
                .width(150.dp)
        )

        SignUpWithButton(
            modifier = Modifier.fillMaxWidth(),
            text = "S'identifier avec Facebook",
            icon = R.drawable.ic_facebook
        ) {

        }

        Spacer(modifier = Modifier.padding(0.dp, 8.dp))

        SignUpWithButton(
            modifier = Modifier.fillMaxWidth(),
            text = "S'identifier avec Google",
            icon = R.drawable.ic_google
        ) {

        }

        Spacer(modifier = Modifier.padding(0.dp, 8.dp))

        SignUpWithButton(
            modifier = Modifier.fillMaxWidth(),
            text = "S'identifier avec Twitter",
            icon = R.drawable.ic_twitter
        ) {

        }
    }
}


@Preview
@Composable
private fun PreviewNewsScreenLoadedEmpty() {
    LoginScreenLoaded()
}