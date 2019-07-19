package cn.edu.whu.unsc.audioinfo

import android.media.AudioFormat
import android.media.MediaRecorder

class AudioRecordConfiguration(
    val mAudioSource: Int = MediaRecorder.AudioSource.DEFAULT,
    val mSampleRate: Int = 44100,
    val mChannelConfig: Int = AudioFormat.CHANNEL_IN_MONO,
    val mEncoding: Int = AudioFormat.ENCODING_PCM_16BIT
) {
    var mMinBufferSize: Int = 0
    var mChannelCount: Int = 0
    var mIsValidConfig: Boolean = false

    companion object {
        fun getAudioSourceName(fAudioSource: Int) = when (fAudioSource) {
            MediaRecorder.AudioSource.DEFAULT -> "DEFAULT"
            MediaRecorder.AudioSource.MIC -> "MIC"
            MediaRecorder.AudioSource.VOICE_UPLINK -> "VOICE_UPLINK"
            MediaRecorder.AudioSource.VOICE_DOWNLINK -> "VOICE_DOWNLINK"
            MediaRecorder.AudioSource.VOICE_CALL -> "VOICE_CALL"
            MediaRecorder.AudioSource.CAMCORDER -> "CAMCORDER"
            MediaRecorder.AudioSource.VOICE_RECOGNITION -> "VOICE_RECOGNITION"
            MediaRecorder.AudioSource.VOICE_COMMUNICATION -> "VOICE_COMMUNICATION"
            MediaRecorder.AudioSource.REMOTE_SUBMIX -> "REMOTE_SUBMIX"
            MediaRecorder.AudioSource.UNPROCESSED -> "UNPROCESSED"
            1998 -> "RADIO_TUNER"
            1999 -> "HOTWORD"
            else -> "AUDIO_SOURCE_INVALID"
        }

        fun getAudioChannelConfigName(fAudioChannelConfig: Int) = when (fAudioChannelConfig) {
            AudioFormat.CHANNEL_IN_DEFAULT -> "CHANNEL_IN_DEFAULT"
            AudioFormat.CHANNEL_IN_MONO -> "CHANNEL_IN_MONO"
            AudioFormat.CHANNEL_IN_STEREO -> "CHANNEL_IN_STEREO"
            else -> "CHANNEL_INVALID"
        }

        fun getAudioEncodingName(fAudioEncoding: Int) = when (fAudioEncoding) {
            AudioFormat.ENCODING_PCM_16BIT -> "ENCODING_PCM_16BIT"
            AudioFormat.ENCODING_PCM_FLOAT -> "ENCODING_PCM_FLOAT"
            else -> "ENCODING_INVALID"
        }
    }
}