package functional

import com.fasterxml.jackson.databind.ObjectMapper
import com.jessecoyle.JCredStash
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Ignore
import org.junit.Test
import java.net.HttpURLConnection
import java.net.URL
import java.io.InputStreamReader
import java.io.BufferedReader
import sun.net.www.protocol.https.HttpsURLConnectionImpl
import java.math.BigInteger


class DeployTest {
    var token = ""
    val ENVIRONMENT = System.getenv("ENV") ?: "test"
    // To do the deploy test before submitting a pull request, change testUrl to "http://localhost:8080"
    var testUrl = "https://xref-api-ecs.sps$ENVIRONMENT.in"
    var testDataXrefId = ""
    var testVendorXrefId = ""
    var testTurnaroundXrefId = ""
    var dataXrefLookupIdResult = ""
    var vendorXrefLookupIdResult = ""
    var turnaroundXrefLookupIdResult = ""
    var dataXrefLookupKeyResult = ""
    var vendorXrefLookupKeyResult = ""
    var turnaroundXrefLookupKey3Result = ""
    var dataXrefLookupValueResult = ""
    var vendorXrefLookupValueResult = ""
    var turnaroundXrefLookupValueResult = ""
    var dataXrefSearchIdResult = ""
    var dataXrefSearchKeyResult = ""
    var dataXrefSearchValueResult = ""
    val jCredStash = JCredStash()

    init {
        when (ENVIRONMENT) {
            "test" -> {
                testDataXrefId = "3"
                testVendorXrefId = "1"
                testTurnaroundXrefId = "1"
                dataXrefLookupIdResult = "CampingWorldAPP"
                vendorXrefLookupIdResult = "quicktestAPP"
                turnaroundXrefLookupIdResult = "quicktest"
                dataXrefLookupKeyResult = "CAR"
                vendorXrefLookupKeyResult = "55681"
                turnaroundXrefLookupKey3Result = "501704"
                dataXrefLookupValueResult = "PP"
                vendorXrefLookupValueResult = "096WESTCOAST"
                turnaroundXrefLookupValueResult = "2008-12-10"
                dataXrefSearchIdResult = "CampingWorldAPP"
                dataXrefSearchKeyResult = "ALW"
                dataXrefSearchValueResult = "DF"
            }
        }
        token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcmdfbmFtZSI6IlNQUyBDb21tZXJjZSIsImxhc3RfbmFtZSI6IkxpcHNvbiIsImV4cCI6MTUwMjIyNTcwMCwiYXZhdGFyX2ltYWdlX3VybCI6Imh0dHBzOi8vZDJkN2d5OHhueWtyZmkuY2xvdWRmcm9udC5uZXQvdXNlci1pbWFnZXMvdGVzdC80MDB4NDAwLzI3NDQ2MTEyMTU4NjE5MzE5MTU3NzA5NTM3MDUyMTU3Mzg0ODIwMy02OTgxNmQ1NDYwMzFmZjFiMmQ4MDVjYjMwNGJiNDAzNzdkNWY4NTNjIiwidmVyIjoiMSIsImlkIjoiNTE3NDUxNzA4NTk4NDIxMjg4NzQ1MTA5OTg3NjMzNjE0NTQwOTIiLCJmaXJzdF9uYW1lIjoiQmVuIiwidXNlcl9pZCI6IjI3NDQ2MTEyMTU4NjE5MzE5MTU3NzA5NTM3MDUyMTU3Mzg0ODIwMyIsIm9yZ19pZCI6IjIwOTA5ODgwODMwNTU2MjQ0NjgxMTI2NTM0MTAzMzQ1MzAxNTcwMyIsInVyaSI6Imh0dHBzOi8vdGVzdC5pZC5zcHNjLmlvIiwiZW52IjoidGVzdCIsImlhdCI6MTUwMjEzOTMwMCwiZW1haWwiOiJiamxpcHNvbkBzcHNjb21tZXJjZS5jb20ifQ.vQggMejFuCOzAbHnwxRgIT9gPL7xpiFOH0P-2jaFu3RuRfQ56_CB8KawiJCAg2pb5pBn5yGmFC4zmNkI18PZAxH3zzOYuB_PaAwSWD0J37FDRc5Zklxny72VOF6HmzypZwHh2i-L2NxJeN-cb5XeMj4TMDBOtpZUPdbQI3mf4VPGC8nUk3Mq3lz8d2QXU3NGGXNOjzHwZYdpAPrJNr5JWkEdzgALKNastdwBWVZzE-U40hibS1Dxo6oIbmnXCaiiWl0NaRX34LDKWMB-C404jE6fWyh1S3Z1kTdBpiX5QAXzkfvcsCWPmgTQ9Nzp9yZy2Z9al4ptaUbqf0BQ7M5z6Q"//jCredStash.getSecret("$ENVIRONMENT-credstash", "web.xref.api.identitytoken", null)
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
    }

    class TestVendorXref {
        val created_by: String? = null
        val created_date: java.sql.Timestamp? = null
        val lookup_id: String? = null
        val lookup_key: String? = null
        val lookup_value: String? = null
        val lookup_value2: String? = null
        val migration_source_systemid: String? = null
        val migration_source_uid: String? = null
        val modified_by: String? = null
        val modified_date: java.sql.Timestamp? = null
        val row_version: Long? = null
        val vendor_xref_id: java.math.BigInteger? = null
    }

    class TestTurnaroundXref {
        val expiry_days: BigInteger? = null
        val id: BigInteger? = null
        val location: String? = null
        val lookup_id: String? = null
        val lookup_id2: String? = null
        val lookup_id3: String? = null
        val lookup_key: String? = null
        val lookup_key2: String? = null
        val lookup_key3: String? = null
        val lookup_key4: String? = null
        val lookup_key5: String? = null
        val lookup_key6: String? = null
        val lookup_value: String? = null
        val status: String? = null
        val supseded_by: String? = null
        val time_stamp: java.sql.Timestamp? = null
        val turnaround_time_stamp: java.sql.Timestamp? = null
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

//    @Test
//    fun testGetVendorXrefById() {
//        testUrl += "/vendorxrefs/" + testVendorXrefId
//        val result = StringBuilder()
//        val url = URL(testUrl)
//        val conn = url.openConnection() as HttpURLConnection
//        conn.requestMethod = "GET"
//        conn.setRequestProperty("Authorization", "Bearer " + token)
//        val rd = BufferedReader(InputStreamReader(conn.inputStream))
//        val line = rd.readLine()
//        result.append(line)
//        rd.close()
//        val vendorXref = ObjectMapper().readValue<TestVendorXref>(result.toString(), TestVendorXref::class.java)
//        assertEquals(vendorXrefLookupIdResult, vendorXref.lookup_id)
//        assertEquals(vendorXrefLookupKeyResult, vendorXref.lookup_key)
//        assertEquals(vendorXrefLookupValueResult, vendorXref.lookup_value)
//    }
//
//    @Test
//    @Ignore
//    fun testGetTurnaroundXrefById() {
//        testUrl += "/turnaroundxrefs/" + testTurnaroundXrefId
//        val result = StringBuilder()
//        val url = URL(testUrl)
//        val conn = url.openConnection() as HttpURLConnection
//        conn.requestMethod = "GET"
//        conn.setRequestProperty("Authorization", "Bearer " + token)
//        val rd = BufferedReader(InputStreamReader(conn.inputStream))
//        val line = rd.readLine()
//        result.append(line)
//        rd.close()
//        val turnaroundXref = ObjectMapper().readValue<TestTurnaroundXref>(result.toString(), TestTurnaroundXref::class.java)
//        assertEquals(turnaroundXrefLookupIdResult, turnaroundXref.lookup_id)
//        assertEquals(turnaroundXrefLookupKey3Result, turnaroundXref.lookup_key3)
//        assertEquals(turnaroundXrefLookupValueResult, turnaroundXref.lookup_value)
//    }
//
//    @Test
//    fun testEmptySearchDataXrefs() {

//    }
//
//    @Test
//    fun testSearchVendorXrefs() {
//        testUrl += "/vendorxrefs?lookup_id=quicktestAPP&lookup_value=096WESTCOAST&page=1&count=100"
//        val result = StringBuilder()
//        val url = URL(testUrl)
//        val conn = url.openConnection() as HttpURLConnection
//        conn.requestMethod = "GET"
//        conn.setRequestProperty("Authorization", "Bearer " + token)
//        val rd = BufferedReader(InputStreamReader(conn.inputStream))
//        var line: String? = ""
//        while(line != null) {
//            line = rd.readLine()
//            result.append(line)
//        }
//        val vendorXrefs = ObjectMapper().readValue(result.toString(), Array<TestVendorXref>::class.java)
//        rd.close()
//        assertEquals(1, vendorXrefs.size)
//        assertEquals(vendorXrefLookupIdResult, vendorXrefs[0].lookup_id)
//        assertEquals(vendorXrefLookupKeyResult, vendorXrefs[0].lookup_key)
//        assertEquals(vendorXrefLookupValueResult, vendorXrefs[0].lookup_value)
//    }
//
//
//    @Test
//    fun testUpdateVendorXref() {
//        testUrl += "/vendorxrefs/" + testVendorXrefId
//        var result = StringBuilder()
//        val url = URL(testUrl)
//        var patchData = JSONObject()
//        patchData.put("lookup_id", "UNIQUETESTLOOKUPID")
//        var conn = url.openConnection() as HttpURLConnection
//        conn.doOutput = true
//        setRequestMethod(conn, "PATCH")
//        conn.setRequestProperty("Content-Type", "application/json")
//        conn.setRequestProperty("Authorization", "Bearer " + token)
//        var wr = conn.outputStream
//        wr.write(patchData.toString().toByteArray())
//        wr.flush()
//        var rd = BufferedReader(InputStreamReader(conn.inputStream))
//        var line = rd.readLine()
//        result.append(line)
//        var res = result.toString()
//        assertEquals("""{"msg":"Successfully updated vendor xref","id":1}""", res)
//
//        testUrl += "/vendorxrefs/" + testVendorXrefId
//        result = StringBuilder()
//        conn = url.openConnection() as HttpURLConnection
//        conn.requestMethod = "GET"
//        conn.setRequestProperty("Authorization", "Bearer " + token)
//        rd = BufferedReader(InputStreamReader(conn.inputStream))
//        line = rd.readLine()
//        result.append(line)
//        rd.close()
//        val vendorXref = ObjectMapper().readValue<TestVendorXref>(result.toString(), TestVendorXref::class.java)
//        assertEquals("UNIQUETESTLOOKUPID", vendorXref.lookup_id)
//        assertEquals(vendorXrefLookupKeyResult, vendorXref.lookup_key)
//        assertEquals(vendorXrefLookupValueResult, vendorXref.lookup_value)
//
//        // Revert the update
//        testUrl += "/vendorxrefs/" + testVendorXrefId
//        result = StringBuilder()
//        patchData = JSONObject()
//        patchData.put("lookup_id", vendorXrefLookupIdResult)
//        conn = url.openConnection() as HttpURLConnection
//        conn.doOutput = true
//        setRequestMethod(conn, "PATCH")
//        conn.setRequestProperty("Content-Type", "application/json")
//        conn.setRequestProperty("Authorization", "Bearer " + token)
//        wr = conn.outputStream
//        wr.write(patchData.toString().toByteArray())
//        wr.flush()
//        rd = BufferedReader(InputStreamReader(conn.inputStream))
//        line = rd.readLine()
//        result.append(line)
//        res = result.toString()
//        assertEquals("""{"msg":"Successfully updated vendor xref","id":1}""", res)
//    }
//
//
//    @Test
//    fun testCreateAndDeleteVendorXref() {
//        testUrl += "/vendorxrefs"
//        var result = StringBuilder()
//        var url = URL(testUrl)
//        var postData = JSONObject()
//        postData.put("created_by", "TESTCREATEDBY")
//        postData.put("lookup_id", "TESTLOOKUPID")
//        postData.put("lookup_key", "TESTLOOKUPKEY")
//        postData.put("lookup_value", "TESTLOOKUPVALUE")
//        postData.put("lookup_value2", "TESTLOOKUPVALUE2")
//        postData.put("modified_by", "TESTMODIFIEDBY")
//        var conn = url.openConnection() as HttpURLConnection
//        conn.doOutput = true
//        conn.requestMethod = "POST"
//        conn.setRequestProperty("Content-Type", "application/json")
//        conn.setRequestProperty("Authorization", "Bearer " + token)
//        var wr = conn.outputStream
//        wr.write(postData.toString().toByteArray())
//        wr.flush()
//        var rd = BufferedReader(InputStreamReader(conn.inputStream))
//        var line = rd.readLine()
//        result.append(line)
//        var res = result.toString()
//        val createdXrefId = res.substring(47, res.length-1)
//        assertEquals("{\"msg\":\"Successfully created vendor xref\",\"id\":", res.substring(0, 47))
//
//        // Delete the xref that was just created
//        testUrl += "/" + createdXrefId
//        result = StringBuilder()
//        url = URL(testUrl)
//        conn = url.openConnection() as HttpURLConnection
//        conn.requestMethod = "DELETE"
//        conn.setRequestProperty("Authorization", "Bearer " + token)
//        rd = BufferedReader(InputStreamReader(conn.inputStream))
//        line = rd.readLine()
//        result.append(line)
//        res = result.toString()
//        assertEquals("{\"msg\":\"Successfully deleted vendor xref\",\"id\":$createdXrefId}", res)
//    }


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
