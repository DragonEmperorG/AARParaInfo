package cn.edu.whu.unsc.audioinfo

import android.Manifest
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import mu.KotlinLogging


class MainActivity : AppCompatActivity() {

    private lateinit var mAudioInfoTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializePermissions()
        initializeUI()
        setAudioRecordConfigurationInfo()
    }

    private fun initializePermissions() {
        initializeMICROPHONEPermissionGroup()
    }

    private fun initializeMICROPHONEPermissionGroup() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.RECORD_AUDIO
                )
            ) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    0
                )

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    private fun initializeUI() {
        mAudioInfoTextView = findViewById(R.id.audio_info_text_view)
        mAudioInfoTextView.movementMethod = ScrollingMovementMethod.getInstance()
    }

    private fun setAudioRecordConfigurationInfo() {

        /**
         * Configure Test AudioRecord Parameters Combination
         */
        val mAudioRecordConfSet: MutableSet<AudioRecordConfiguration> = mutableSetOf()

        val mValidAudioSources: Array<Int> = arrayOf(
//            MediaRecorder.AudioSource.DEFAULT,
            MediaRecorder.AudioSource.MIC,
            MediaRecorder.AudioSource.UNPROCESSED
        )

        val mValidSampleRates: Array<Int> = arrayOf(
            8000,
            11025,
            16000,
            22050,
            32000,
            37800,
            44056,
            44100,
            47250,
            48000,
            50000,
            50400,
            88200,
            96000,
            176400,
            192000,
            352800,
            2822400,
            5644800
        )

        val mValidChannelConfigs: Array<Int> = arrayOf(
//            AudioFormat.CHANNEL_IN_DEFAULT,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.CHANNEL_IN_STEREO
        )

        val mValidEncodings: Array<Int> = arrayOf(
            AudioFormat.ENCODING_PCM_16BIT,
            AudioFormat.ENCODING_PCM_FLOAT
        )

        for (tValidAudioSource in mValidAudioSources) {
            for (tValidSampleRate in mValidSampleRates) {
                for (tValidChannelConfig in mValidChannelConfigs) {
                    for (tValidEncoding in mValidEncodings) {
                        mAudioRecordConfSet.add(
                            AudioRecordConfiguration(
                                tValidAudioSource,
                                tValidSampleRate,
                                tValidChannelConfig,
                                tValidEncoding
                            )
                        )
                    }
                }
            }
        }


//        mAudioRecordConfSet.add(
//            AudioRecordConfiguration(
//                MediaRecorder.AudioSource.MIC,
//                176400,
//                AudioFormat.CHANNEL_IN_MONO,
//                AudioFormat.ENCODING_PCM_16BIT
//            )
//        )

        /**
         * Test AudioRecord Parameter
         */
        for (tAudioRecordConf in mAudioRecordConfSet) {
            tAudioRecordConf.mMinBufferSize = AudioRecord.getMinBufferSize(
                tAudioRecordConf.mSampleRate,
                tAudioRecordConf.mChannelConfig,
                tAudioRecordConf.mEncoding
            )

            if (tAudioRecordConf.mMinBufferSize > 0) {
                var tAudioRecord: AudioRecord? = null
                try {
                    tAudioRecord = AudioRecord(
                        tAudioRecordConf.mAudioSource,
                        tAudioRecordConf.mSampleRate,
                        tAudioRecordConf.mChannelConfig,
                        tAudioRecordConf.mEncoding,
                        tAudioRecordConf.mMinBufferSize
                    )
                    val tBuffer = ByteArray(tAudioRecordConf.mMinBufferSize)

                    tAudioRecordConf.mIsValidConfig = true
                    tAudioRecordConf.mChannelCount = tAudioRecord.channelCount

//                    tAudioRecord.startRecording()
//                    val tReadBufferSize = tAudioRecord.read(tBuffer, 0, tBuffer.size)
//                    if (tReadBufferSize >= 0) {
//                        tAudioRecordConf.mIsValidConfig = true
//                        tAudioRecordConf.mChannelCount = tAudioRecord.channelCount
//                    }
                } catch (e: Exception) {
                    mLogger.error { e }
                } finally {
                    if (tAudioRecord != null) {
                        try {
                            if (tAudioRecord.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                                tAudioRecord.stop()
                            }
                        } catch (ex: IllegalStateException) {
                            //
                        }

                        if (tAudioRecord.state == AudioRecord.STATE_INITIALIZED) {
                            tAudioRecord.release()
                        }
                    }
                }
            }
        }


        /**
         * Show Supported AudioRecord Configuration
         */
        var mValidConfig = "| AudioSource | SampleRate | Channel | Encoding |  MinBufferSize  |\n"
        mAudioRecordConfSet
            .filter { it.mIsValidConfig }
            .forEach {
                mValidConfig += ("|" + String.format("%13s", AudioRecordConfiguration.getAudioSourceName(it.mAudioSource)) + "|"
                        + "     " + String.format("%6d", it.mSampleRate) + " |"
                        + "       " + "${it.mChannelCount}" + " |"
                        + " ${AudioRecordConfiguration.getAudioEncodingName(it.mEncoding)} |"
                        + " " + String.format("%5d", it.mMinBufferSize) + " |"
                        + "\n")
            }
        mAudioInfoTextView.text = mValidConfig
    }

    companion object {
        private val mLogger = KotlinLogging.logger {}
    }
}
