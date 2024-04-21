package com.example.kotlinspringcrudwebapi

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository

/**
 * Customer テーブルを操作するメソッドをまとめたインタフェース
 */
interface CustomerRepository {
    /**
     * データ新規登録
     *
     * @param firstName 名前
     * @param lastName 名字
     */
    fun add(firstName: String, lastName: String)

    /**
     * データ一覧取得
     *
     * @return
     */
    fun find(): List<Customer>

    /**
     * データ更新
     *
     * @param id プライマリID
     * @param firstName 名前
     * @param lastName 名字
     */
    fun update(id: Int, firstName: String, lastName: String)

    /**
     * データ削除
     *
     * @param id プライマリID
     */
    fun delete(id: Int)
}

/**
 * CustomerRepository 実装クラス
 *
 * 「@Repository」: DIするためのアノテーション
 * 参考：https://zenn.dev/tokium_dev/articles/dependency-injection-watanabe
 *
 * @property namedParameterJdbcTemplate 簡単にSQLを実行するためのオブジェクト
 */
@Repository
class CustomerRepositoryImpl(val namedParameterJdbcTemplate: NamedParameterJdbcTemplate) : CustomerRepository {
    /**
     * データ新規登録
     *
     * @param firstName 名前
     * @param lastName 名字
     */
    override fun add(firstName: String, lastName: String) {
        // 実行SQL
        val sql = """
            INSERT INTO
                customer (
                    first_name
                    , last_name
                )
            VALUES (
                :first_name
                , :last_name
            )
            ;
        """.trimIndent()

        // SQLの名前付きパラメータ
        val sqlParams = MapSqlParameterSource()
            .addValue("first_name", firstName)
            .addValue("last_name", lastName)

        // SQL実行(INSERT/UPDATE/DELETE)
        namedParameterJdbcTemplate.update(sql, sqlParams)

        return
    }

    /**
     * データ一覧取得
     *
     * @return
     */
    override fun find(): List<Customer> {
        // 実行SQL
        val sql = """
            SELECT
                id
                , first_name
                , last_name
            FROM
                customer
            ;
        """.trimIndent()

        // SQLの名前付きパラメータ
        val sqlParams = MapSqlParameterSource()

        // 複数行のレコード取得
        val customerMap = namedParameterJdbcTemplate.queryForList(sql, sqlParams)

        // 取得結果を返す
        return customerMap.map {
            Customer(
                it["id"].toString().toInt().toLong(),
                it["first_name"].toString(),
                it["last_name"].toString(),
            )
        }
    }

    /**
     * データ更新
     *
     * @param id プライマリID
     * @param firstName 名前
     * @param lastName 名字
     */
    override fun update(id: Int, firstName: String, lastName: String) {
        // 実行SQL
        val sql = """
            UPDATE
                customer
            SET
                first_name = :first_name
                , last_name = :last_name
            WHERE
                id = :id
        """.trimIndent()

        // SQLの名前付きパラメータ
        val sqlParams = MapSqlParameterSource()
            .addValue("first_name", firstName)
            .addValue("last_name", lastName)
            .addValue("id", id)

        // SQL実行(INSERT/UPDATE/DELETE)
        namedParameterJdbcTemplate.update(sql, sqlParams)

        return
    }

    /**
     * データ削除
     *
     * @param id プライマリID
     */
    override fun delete(id: Int) {
        // 実行SQL
        val sql = """
            DELETE FROM
                customer
            WHERE
                id = :id
            ;
        """.trimIndent()

        // SQLの名前付きパラメータ
        val sqlParams = MapSqlParameterSource()
            .addValue("id", id)

        // SQL実行(INSERT/UPDATE/DELETE)
        namedParameterJdbcTemplate.update(sql, sqlParams)

        return
    }
}
