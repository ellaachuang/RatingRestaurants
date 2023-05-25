package com.example.loginandregistration

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest {

    //PASSWORD!!!!!!---------------------------------------------------------------------------
    @Test
    fun validatePassword_emptyPassword_isFalse(){
        val actual = RegistrationUtil.validatePassword("","")
        assertThat(actual).isFalse()
    }

    @Test
    fun validatePassword_passwordDontMatch_isFalse(){
        val actual = RegistrationUtil.validatePassword("A1bcdefg", "1Acvdefg")
        assertThat(actual).isFalse()
    }

    @Test
    fun validatePassword_passwordLength_isFalse(){
        val actual = RegistrationUtil.validatePassword("A1", "A1")
        assertThat(actual).isFalse()
    }

    @Test
    fun validatePassword_capitalInPassword_isFalse(){
        val actual = RegistrationUtil.validatePassword("aaaaaaa1", "aaaaaaa1")
        assertThat(actual).isFalse()
    }

    @Test
    fun validatePassword_numInPassword_isFalse(){
        val actual = RegistrationUtil.validatePassword("Aaaaaaaa", "Aaaaaaaa")
        assertThat(actual).isFalse()
    }

    @Test
    fun validatePassword_numInPassword1_isTrue(){
        val actual = RegistrationUtil.validatePassword("Aaaa1aaaa", "Aaaa1aaaa")
        assertThat(actual).isTrue()
    }

    @Test
    fun validatePassword_numInPassword2_isTrue(){
        val actual = RegistrationUtil.validatePassword("1Aaaaaaaa", "1Aaaaaaaa")
        assertThat(actual).isTrue()
    }

    @Test
    fun validatePassword_numInPassword3_isTrue(){
        val actual = RegistrationUtil.validatePassword("Aaaaaaaa1", "Aaaaaaaa1")
        assertThat(actual).isTrue()
    }

    @Test
    fun validatePassword_perfect_isTrue(){
        val actual = RegistrationUtil.validatePassword("Abcedfg1", "Abcedfg1")
        assertThat(actual).isTrue()
    }



    //USERNAME!!!!!!------------------------------------------------------------
    @Test
    fun validateUsername_emptyUsername_isFalse(){
        val actual = RegistrationUtil.validateUsername("")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateUsername_usernameLength_isFalse(){
        val actual = RegistrationUtil.validateUsername("aa")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateUsername_usernameNotTaken_isFalse(){
        val actual = RegistrationUtil.validateUsername("bob")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateUsername_usernameNotTaken2_isFalse(){
        val actual = RegistrationUtil.validateUsername("cosmicF")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateUsername_usernameNotTaken3_isFalse(){
        val actual = RegistrationUtil.validateUsername("alice")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateUsername_perfect_isTrue(){
        val actual = RegistrationUtil.validateUsername("ella")
        assertThat(actual).isTrue()
    }



    //NAME!!!!!!------------------------------------------------------------
    @Test
    fun validateName_notNull_isFalse(){
        val actual = RegistrationUtil.validateName("")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateName_perfect_isTrue(){
        val actual = RegistrationUtil.validateName("Ella")
        assertThat(actual).isTrue()
    }

    //EMAIL!!!!!!------------------------------------------------------------
    @Test
    fun validateEmail_notNull_isFalse(){
        val actual = RegistrationUtil.validateEmail("")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateEmail_notUsedBefore1_isFalse(){
        val actual = RegistrationUtil.validateEmail("cosmicF@gmail.com")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateEmail_notUsedBefore2_isFalse(){
        val actual = RegistrationUtil.validateEmail("bob@gmail.com")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateEmail_notUsedBefore3_isFalse(){
        val actual = RegistrationUtil.validateEmail("alice@gmail.com")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateEmail_emailHasCom_isFalse(){
        val actual = RegistrationUtil.validateEmail("alice@gmail")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateEmail_emailHasAt_isFalse(){
        val actual = RegistrationUtil.validateEmail("alicegmail.com")
        assertThat(actual).isFalse()
    }

    @Test
    fun validateEmail_perfect_isTrue(){
        val actual = RegistrationUtil.validateEmail("ella@gmail.com")
        assertThat(actual).isTrue()
    }


}