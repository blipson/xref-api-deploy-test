package functional

import com.fasterxml.jackson.databind.ObjectMapper
import com.jessecoyle.JCredStash
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Test
import java.net.HttpURLConnection
import java.net.URL
import java.io.InputStreamReader
import java.io.BufferedReader
import sun.net.www.protocol.https.HttpsURLConnectionImpl
import java.sql.Timestamp


class DeployTest {
    var token = ""
    val ENVIRONMENT = System.getenv("ENV") ?: "test"
    // To do the deploy test before submitting a pull request, change testUrl to "http://localhost:8080"
    var testUrl = "https://xref-api-ecs.sps$ENVIRONMENT.in"
    val jCredStash = JCredStash()

    init {
        when (ENVIRONMENT) {
            "test" -> {
            }
        }
        token = jCredStash.getSecret("$ENVIRONMENT-credstash", "web.xref.api.identitytoken", null)
    }

    class TestDataXref {
        val data_xref_id: java.math.BigInteger? = null
        val lookup_id: String? = null
        val lookup_id2: String? = null
        val lookup_id3: String? = null
        val lookup_key: String? = null
        val lookup_key2: String? = null
        val lookup_key3: String? = null
        val lookup_value: String? = null
        val migration_source_systemid: String? = null
        val migration_source_uid: String? = null
        val owner_key: String? = null
        val created_by: String? = null
        val created_date: Timestamp? = null
        val modified_by: String? = null
        val modified_date: Timestamp? = null
    }

    @Test
    fun testDataXrefsSingle() {
        // Create Data XREF
        val createRes = createDataXref()
        assertEquals("{\"msg\":\"Successfully created data xref\",\"id\":", createRes.substring(0, 45))
        val createdXrefId = createRes.substring(45, createRes.length-1)

        // Get Data XREF by ID
        val dataXrefsSingleTestUrl = testUrl + "/dataxrefs/" + createdXrefId
        val getByIdResult = StringBuilder()
        val getByIdUrl = URL(dataXrefsSingleTestUrl)
        val getByIdConn = getByIdUrl.openConnection() as HttpURLConnection
        getByIdConn.requestMethod = "GET"
        getByIdConn.setRequestProperty("Authorization", "Bearer " + token)
        val getByIdRd = BufferedReader(InputStreamReader(getByIdConn.inputStream))
        val getByIdLine = getByIdRd.readLine()
        getByIdResult.append(getByIdLine)
        getByIdRd.close()
        val getByIdDataXref = ObjectMapper().readValue<TestDataXref>(getByIdResult.toString(), TestDataXref::class.java)
        assertEquals("DEPLOYTESTLOOKUPID", getByIdDataXref.lookup_id)
        assertEquals("DEPLOYTESTLOOKUPID2", getByIdDataXref.lookup_id2)
        assertEquals("DEPLOYTESTLOOKUPID3", getByIdDataXref.lookup_id3)
        assertEquals("DEPLOYTESTLOOKUPKEY", getByIdDataXref.lookup_key)
        assertEquals("DEPLOYTESTLOOKUPKEY2", getByIdDataXref.lookup_key2)
        assertEquals("DEPLOYTESTLOOKUPKEY3", getByIdDataXref.lookup_key3)
        assertEquals("DEPLOYTESTLOOKUPVALUE", getByIdDataXref.lookup_value)


        // Update Data XREF
        var updateResult = StringBuilder()
        val updateUrl = URL(dataXrefsSingleTestUrl)
        val updatePatchData = JSONObject()
        updatePatchData.put("lookup_id", "UPDATETESTLOOKUPID")
        var updateConn = updateUrl.openConnection() as HttpURLConnection
        updateConn.doOutput = true
        setRequestMethod(updateConn, "PATCH")
        updateConn.setRequestProperty("Content-Type", "application/json")
        updateConn.setRequestProperty("Authorization", "Bearer " + token)
        val updateWr = updateConn.outputStream
        updateWr.write(updatePatchData.toString().toByteArray())
        updateWr.flush()
        var updateRd = BufferedReader(InputStreamReader(updateConn.inputStream))
        var updateLine = updateRd.readLine()
        updateResult.append(updateLine)
        val updateRes = updateResult.toString()
        assertEquals("{\"msg\":\"Successfully updated data xref\",\"id\":$createdXrefId}", updateRes)

        updateResult = StringBuilder()
        updateConn = updateUrl.openConnection() as HttpURLConnection
        updateConn.requestMethod = "GET"
        updateConn.setRequestProperty("Authorization", "Bearer " + token)
        updateRd = BufferedReader(InputStreamReader(updateConn.inputStream))
        updateLine = updateRd.readLine()
        updateResult.append(updateLine)
        updateRd.close()
        val dataXref = ObjectMapper().readValue<TestDataXref>(updateResult.toString(), TestDataXref::class.java)
        assertEquals("UPDATETESTLOOKUPID", dataXref.lookup_id)
        assertEquals("DEPLOYTESTLOOKUPID2", getByIdDataXref.lookup_id2)
        assertEquals("DEPLOYTESTLOOKUPID3", getByIdDataXref.lookup_id3)
        assertEquals("DEPLOYTESTLOOKUPKEY", getByIdDataXref.lookup_key)
        assertEquals("DEPLOYTESTLOOKUPKEY2", getByIdDataXref.lookup_key2)
        assertEquals("DEPLOYTESTLOOKUPKEY3", getByIdDataXref.lookup_key3)
        assertEquals("DEPLOYTESTLOOKUPVALUE", getByIdDataXref.lookup_value)


        // Delete Data XREF
        val deleteResult = StringBuilder()
        val deleteUrl = URL(dataXrefsSingleTestUrl)
        val deleteConn = deleteUrl.openConnection() as HttpURLConnection
        deleteConn.requestMethod = "DELETE"
        deleteConn.setRequestProperty("Authorization", "Bearer " + token)
        val deleteRd = BufferedReader(InputStreamReader(deleteConn.inputStream))
        val deleteLine = deleteRd.readLine()
        deleteResult.append(deleteLine)
        val deleteRes = deleteResult.toString()
        assertEquals("{\"msg\":\"Successfully deleted data xref\",\"id\":$createdXrefId}", deleteRes)
    }

    @Test
    fun testDataXrefsMultiple() {
        // Create first Data XREF
        val firstCreateRes = createDataXref()
        assertEquals("{\"msg\":\"Successfully created data xref\",\"id\":", firstCreateRes.substring(0, 45))
        val firstCreatedXrefId = firstCreateRes.substring(45, firstCreateRes.length-1)

        // Create second Data XREF
        val secondCreateRes = createDataXref()
        assertEquals("{\"msg\":\"Successfully created data xref\",\"id\":", secondCreateRes.substring(0, 45))
        val secondCreatedXrefId = secondCreateRes.substring(45, secondCreateRes.length-1)

        // Search Data XREFs
        val multipleSearchTestUrl = testUrl + "/dataxrefs?lookup_id=DEPLOYTESTLOOKUPID&lookup_id2=DEPLOYTESTLOOKUPID2&lookup_id3=DEPLOYTESTLOOKUPID3&lookup_key=DEPLOYTESTLOOKUPKEY&lookup_key2=DEPLOYTESTLOOKUPKEY2&lookup_key3=DEPLOYTESTLOOKUPKEY3&lookup_value=DEPLOYTESTLOOKUPVALUE&page=0&count=100&sort=data_xref_id:asc"
        val result = StringBuilder()
        val url = URL(multipleSearchTestUrl)
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        conn.setRequestProperty("Authorization", "Bearer " + token)
        val rd = BufferedReader(InputStreamReader(conn.inputStream))
        var line: String? = ""
        while(line != null) {
            line = rd.readLine()
            result.append(line)
        }
        val dataXrefs = ObjectMapper().readValue(result.toString(), Array<TestDataXref>::class.java)
        rd.close()
        assertEquals(2, dataXrefs.size)
        assertEquals("DEPLOYTESTLOOKUPID", dataXrefs[0].lookup_id)
        assertEquals("DEPLOYTESTLOOKUPID2", dataXrefs[0].lookup_id2)
        assertEquals("DEPLOYTESTLOOKUPID3", dataXrefs[0].lookup_id3)
        assertEquals("DEPLOYTESTLOOKUPKEY", dataXrefs[0].lookup_key)
        assertEquals("DEPLOYTESTLOOKUPKEY2", dataXrefs[0].lookup_key2)
        assertEquals("DEPLOYTESTLOOKUPKEY3", dataXrefs[0].lookup_key3)
        assertEquals("DEPLOYTESTLOOKUPVALUE", dataXrefs[0].lookup_value)
        assertEquals("DEPLOYTESTLOOKUPID", dataXrefs[1].lookup_id)
        assertEquals("DEPLOYTESTLOOKUPID2", dataXrefs[1].lookup_id2)
        assertEquals("DEPLOYTESTLOOKUPID3", dataXrefs[1].lookup_id3)
        assertEquals("DEPLOYTESTLOOKUPKEY", dataXrefs[1].lookup_key)
        assertEquals("DEPLOYTESTLOOKUPKEY2", dataXrefs[1].lookup_key2)
        assertEquals("DEPLOYTESTLOOKUPKEY3", dataXrefs[1].lookup_key3)
        assertEquals("DEPLOYTESTLOOKUPVALUE", dataXrefs[1].lookup_value)

        // Search Data XREFs with distinct
        val distinctSearchTestUrl = testUrl + "/dataxrefs?lookup_id=DEPLOYTESTLOOKUPID&lookup_id2=DEPLOYTESTLOOKUPID2&lookup_id3=DEPLOYTESTLOOKUPID3&lookup_key=DEPLOYTESTLOOKUPKEY&lookup_key2=DEPLOYTESTLOOKUPKEY2&lookup_key3=DEPLOYTESTLOOKUPKEY3&lookup_value=DEPLOYTESTLOOKUPVALUE&page=0&count=100&distinct=lookup_id"
        val distinctResult = StringBuilder()
        val distinctUrl = URL(distinctSearchTestUrl)
        val distinctConn = distinctUrl.openConnection() as HttpURLConnection
        distinctConn.requestMethod = "GET"
        distinctConn.setRequestProperty("Authorization", "Bearer " + token)
        val distinctRd = BufferedReader(InputStreamReader(distinctConn.inputStream))
        var distinctLine: String? = ""
        while(distinctLine != null) {
            distinctLine = distinctRd.readLine()
            distinctResult.append(distinctLine)
        }
        val distinctDataXrefs = ObjectMapper().readValue(distinctResult.toString(), Array<TestDataXref>::class.java)
        distinctRd.close()
        assertEquals(1, distinctDataXrefs.size)
        assertEquals("DEPLOYTESTLOOKUPID", distinctDataXrefs[0].lookup_id)
        assertEquals("DEPLOYTESTLOOKUPID2", distinctDataXrefs[0].lookup_id2)
        assertEquals("DEPLOYTESTLOOKUPID3", distinctDataXrefs[0].lookup_id3)
        assertEquals("DEPLOYTESTLOOKUPKEY", distinctDataXrefs[0].lookup_key)
        assertEquals("DEPLOYTESTLOOKUPKEY2", distinctDataXrefs[0].lookup_key2)
        assertEquals("DEPLOYTESTLOOKUPKEY3", distinctDataXrefs[0].lookup_key3)
        assertEquals("DEPLOYTESTLOOKUPVALUE", distinctDataXrefs[0].lookup_value)

        // Delete Data XREFs
        val multipleDeleteTestUrl = testUrl + "/dataxrefs"
        val deleteResult = StringBuilder()
        val deleteUrl = URL(multipleDeleteTestUrl)

        val deleteArr = JSONArray(listOf(firstCreatedXrefId.toLong(), secondCreatedXrefId.toLong()))

        val deleteConn = deleteUrl.openConnection() as HttpURLConnection
        deleteConn.doOutput = true
        deleteConn.requestMethod = "DELETE"
        deleteConn.setRequestProperty("Content-Type", "application/json")
        deleteConn.setRequestProperty("Authorization", "Bearer " + token)

        val deleteWr = deleteConn.outputStream
        deleteWr.write(deleteArr.toString().toByteArray())
        deleteWr.flush()

        val deleteRd = BufferedReader(InputStreamReader(deleteConn.inputStream))
        val deleteLine = deleteRd.readLine()
        deleteResult.append(deleteLine)
        val deleteRes = deleteResult.toString()
        assertEquals("{\"msg\":\"Successfully deleted data xrefs at [$firstCreatedXrefId, $secondCreatedXrefId]\",\"id\":0}", deleteRes)
    }

    fun createDataXref(): String {
        val createTestUrl = testUrl + "/dataxrefs"
        val createResult = StringBuilder()
        val createUrl = URL(createTestUrl)

        val postData = JSONObject()
        postData.put("lookup_id", "DEPLOYTESTLOOKUPID")
        postData.put("lookup_id2", "DEPLOYTESTLOOKUPID2")
        postData.put("lookup_id3", "DEPLOYTESTLOOKUPID3")
        postData.put("lookup_key", "DEPLOYTESTLOOKUPKEY")
        postData.put("lookup_key2", "DEPLOYTESTLOOKUPKEY2")
        postData.put("lookup_key3", "DEPLOYTESTLOOKUPKEY3")
        postData.put("lookup_value", "DEPLOYTESTLOOKUPVALUE")

        val createConn = createUrl.openConnection() as HttpURLConnection
        createConn.doOutput = true
        createConn.requestMethod = "POST"
        createConn.setRequestProperty("Content-Type", "application/json")
        createConn.setRequestProperty("Authorization", "Bearer " + token)

        val createWr = createConn.outputStream
        createWr.write(postData.toString().toByteArray())
        createWr.flush()

        val createRd = BufferedReader(InputStreamReader(createConn.inputStream))
        val createLine = createRd.readLine()
        val createRes = createResult.append(createLine).toString()
        val createdXrefId = createRes.substring(45, createRes.length-1)
        return createRes
    }

    // Helper method to set the request method on an httpurlconnection
    // Used for PATCH since httpurlconnection class doesnt natively support the PATCH method
    private fun setRequestMethod(c: HttpURLConnection, value: String) {
        try {
            val target: Any
            if (c is HttpsURLConnectionImpl) {
                val delegate = HttpsURLConnectionImpl::class.java.getDeclaredField("delegate")
                delegate.isAccessible = true
                target = delegate.get(c)
            } else {
                target = c
            }
            val f = HttpURLConnection::class.java.getDeclaredField("method")
            f.isAccessible = true
            f.set(target, value)
        } catch (ex: IllegalAccessException) {
            throw AssertionError(ex)
        } catch (ex: NoSuchFieldException) {
            throw AssertionError(ex)
        }
    }
}
