package com.example.kotlinspringcrudwebapi

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * Customer テーブルの CRUD をおこなう Web API のエンドポイントを定義するクラス
 *
 * 「@RestController」: アノテーションされたクラスのメソッドの戻り値がHTTPリクエストの戻り値になる
 *
 * @property customerService カスタマーサービス
 */
@RestController
class CustomerController(val customerService: CustomerService) {
    /**
     * データ新規登録
     *
     * @param request リクエスト
     * @return
     */
    @PostMapping("/customers")
    fun insert(@RequestBody request: CustomerRequest): String {
        // データ新規登録
        customerService.insertCustomer(request.firstName, request.lastName)

        // 処理結果を返す
        return """
            {
                "message": "success"
            }
        """.trimIndent()
    }

    /**
     * データ一覧取得
     *
     * @return
     */
    @GetMapping("/customers")
    fun read(): CustomerResponse {
        // データ一覧取得し結果を返す
        return CustomerResponse(customers = customerService.selectCustomer())
    }

    /**
     * データ更新
     *
     * @param id プライマリID
     * @param request リクエスト
     * @return
     */
    @PutMapping("/customers/{id}")
    fun update(@PathVariable("id") id: Int, @RequestBody request: CustomerRequest): String {
        // データ更新
        customerService.updateCustomer(id, request.firstName, request.lastName)

        // 処理結果を返す
        return """
            {
                "message": "success"
            }
        """.trimIndent()
    }

    /**
     * データ削除
     *
     * @param id プライマリID
     * @return
     */
    @DeleteMapping("/customers/{id}")
    fun delete(@PathVariable("id") id: Int): String {
        // データ削除
        customerService.deleteCustomer(id)

        // 処理結果を返す
        return """
            {
                "message": "success"
            }
        """.trimIndent()
    }
}

/**
 * Customer 作成エンドポイント、Customer 更新エンドポイントのリクエストボディ
 *
 * DTO: データの受け渡し専用クラス
 *
 * @property firstName 名前
 * @property lastName 名字
 */
data class CustomerRequest(
    @JsonProperty("first_name") val firstName: String,
    @JsonProperty("last_name") val lastName: String,
)

/**
 * Customer 一覧取得エンドポイントのレスポンス
 *
 * DTO: データの受け渡し専用クラス
 *
 * @property customers
 */
data class CustomerResponse(
    val customers: List<Customer>,
)
