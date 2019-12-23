package hva.nl.inspiriobot.data

data class MindfulnessResponse(
    var data: Array<MindfulnessElement>,
    var mp3: String

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MindfulnessResponse

        if (!data.contentEquals(other.data)) return false
        if (mp3 != other.mp3) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.contentHashCode()
        result = 31 * result + mp3.hashCode()
        return result
    }
}