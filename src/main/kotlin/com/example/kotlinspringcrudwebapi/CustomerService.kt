package com.example.kotlinspringcrudwebapi

import org.springframework.stereotype.Service

/**
 * Customer 操作をおこなうメソッドをまとめるインタフェース
 */
interface CustomerService {
    /**
     * データ新規登録
     *
     * @param firstName 名前
     * @param lastName 名字
     */
    fun insertCustomer(firstName: String, lastName: String)

    /**
     * データ一覧取得
     *
     * @return
     */
    fun selectCustomer(): List<Customer>

    /**
     * データ更新
     *
     * @param id プライマリID
     * @param firstName 名前
     * @param lastName 名字
     */
    fun updateCustomer(id: Int, firstName: String, lastName: String)

    /**
     * データ削除
     *
     * @param id プライマリID
     */
    fun deleteCustomer(id: Int)
}

/**
 * CustomerService 実装クラス
 *
 *「@Service」: DIするためのアノテーション(DIされているオブジェクトがインターフェースの場合、「@Component」アノテーションが検索対象となる)
 * → ・存在した場合、該当クラスをインスタンス化する
 * → ・存在しない場合、アプリケーションの起動時にエラーが発生する
 *
 * ※ 「@Repository」アノテーションと「@Service」アノテーションは内部的に「@Component」アノテーションを含んでいる
 * → 「@Repository」アノテーションが検索対象となる
 *
 * @property customerRepository　カスタマーリポジトリ
 */
@Service
class CustomerServiceImpl(val customerRepository: CustomerRepository) : CustomerService {
    /**
     * データ新規登録
     *
     * @param firstName 名前
     * @param lastName 名字
     */
    override fun insertCustomer(firstName: String, lastName: String) {
        // データ新規登録
        customerRepository.add(firstName, lastName)

        return
    }

    /**
     * データ一覧取得
     *
     * @return
     */
    override fun selectCustomer(): List<Customer> {
        // 取得結果を返す
        return customerRepository.find()
    }

    /**
     * データ更新
     *
     * @param id プライマリID
     * @param firstName 名前
     * @param lastName 名字
     */
    override fun updateCustomer(id: Int, firstName: String, lastName: String) {
        // データ更新
        customerRepository.update(id, firstName, lastName)

        return
    }

    /**
     * データ削除
     *
     * @param id プライマリID
     */
    override fun deleteCustomer(id: Int) {
        // データ削除
        customerRepository.delete(id)

        return
    }
}
