package fr.skyle.escapy.ui.newroom

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.lifecycle.observe
import fr.skyle.escapy.R
import fr.skyle.escapy.base.AbstractFragment
import fr.skyle.escapy.ext.Duration
import fr.skyle.escapy.ext.snackbar
import fr.skyle.escapy.ext.textTrimmed
import fr.skyle.escapy.ui.main.ActivityMain
import fr.skyle.escapy.ui.room.FragmentRoom
import kotlinx.android.synthetic.main.fragment_new_room.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class FragmentNewRoom : AbstractFragment() {

    private val viewModelNewRoom by viewModel<ViewModelNewRoom>()

    private var selectedDuration = Duration(0, 0)
    private var random: Random? = null

    override val layoutId: Int
        get() = R.layout.fragment_new_room

    // -------------------------------------------------
    // Life cycle
    // -------------------------------------------------

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        random = Random()

        //Set all listeners
        setListeners()
    }

    // -------------------------------------------------
    // Other methods
    // -------------------------------------------------

    private fun setListeners() {
        editTextNewRoomDuration.setOnClickListener {
            TimePickerDialog(
                context, TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                    selectedDuration.hour = selectedHour
                    selectedDuration.minute = selectedMinute
                    editTextNewRoomDuration.setText(
                        getString(
                            R.string.new_room_duration_format,
                            selectedDuration.hourTwoDigits,
                            selectedDuration.minuteTwoDigits
                        )
                    )
                },
                selectedDuration.hour,
                selectedDuration.minute,
                true
            ).show()
        }

        buttonNewRoomCreate.setOnClickListener {
            if (checkIfAllFieldsAreFilled()) {
                createNewRoom()
            }
        }

        viewModelNewRoom.successObs.observe(this) {
            if (it.type == ViewModelNewRoom.SUCCESS.NEW_ROOM_CREATED) {
                (activity as? ActivityMain)?.startFragment(FragmentRoom(), true, true)
                activity?.finish()
            }
        }

        viewModelNewRoom.errorObs.observe(this) {
            if (it == ViewModelNewRoom.ERROR.NO_INTERNET) {
                snackbar("Aucune connexion internet disponible")
            } else if (it == ViewModelNewRoom.ERROR.NEW_ROOM_CREATION_ERROR) {
                snackbar("Une erreur est survenue lors de la création de la salle")
            }
        }
    }

    private fun checkIfAllFieldsAreFilled(): Boolean {
        if (editTextNewRoomDuration.textTrimmed().isEmpty() && selectedDuration.hour == 0 && selectedDuration.minute == 0) {
            snackbar("Veuillez sélectionner une durée")
            return false
        } else if (selectedDuration.hour == 0 && selectedDuration.minute == 0) {
            snackbar("La durée ne peut pas être infèrieure à 1min")
            return false
        } else if (editTextNewRoomTitle.text.isNullOrEmpty()) {
            snackbar("Veuillez donner un titre à la salle")
            return false
        } else if (editTextNewRoomNbPlayers.text.isNullOrEmpty()) {
            snackbar("Veuillez renseigner un nombre de joueurs recommandés")
            return false
        }

        return true
    }

    private fun createNewRoom() {
        viewModelNewRoom.postNewRoom(
            System.currentTimeMillis(),
            editTextNewRoomTitle.textTrimmed(),
            generatePassword(),
            editTextNewRoomNbPlayers.textTrimmed().toInt(),
            selectedDuration.time
        )
    }

    private fun generatePassword(): String {
        return ((random?.nextInt(9999 - 1000 + 1) ?: 0) + 1000).toString()
    }
}