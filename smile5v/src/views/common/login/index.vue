<template>
  <div class="login">
    <div class="login-box">
      <div class="top">
        <div class="logo">
          <img src="@/assets/img/login-logo.png"
               alt=""
          />
        </div>
      </div>

      <div class="mid">
        <el-form
          ref="dataFormRef"
          :model="dataForm"
          :rules="dataRule"
        >
          <el-form-item prop="userName">
            <el-input
                v-model="dataForm.userName"
                class="info"
                placeholder="账号"
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
                v-model="dataForm.password"
                class="info"
                type="password"
                placeholder="密码"
            />
          </el-form-item>
          <el-form-item>
            <div class="item-btn">
              <input
                type="button"
                value="登录"
                @click="dataFormSubmit()"
              />
            </div>
          </el-form-item>
        </el-form>
      </div>

      <div class="bottom">
        Copyright © 2019 恋你的甜有限公司
      </div>
    </div>

    <Verify
      ref="verifyRef"
      @img-size="{width: '400px', height:'200px'}"
      @success="login"
    />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import http from '@/utils/http.js'
import router from '@/router/index.js'
import cookie from 'vue-cookies'

const dataForm = ref({
  userName: '',
  password: '',
  captcha: ''
})

const dataRule = {
  userName: [{ required: true, message: '帐号不能为空', trigger: 'blur' }],
  password: [{ required: true, message: '密码不能为空', trigger: 'blur' }],
  captcha: [{ required: true, message: '验证码不能为空', trigger: 'blur' }]
}

const dataFormRef = ref(null)
const verifyRef = ref(null)
let isSubmit = false

const dataFormSubmit = () => {
  dataFormRef.value?.validate((valid) => {
    if (valid) {
      verifyRef.value?.show()
    }
  })
}

const login = (verifyResult) => {
  if (isSubmit) {
    return
  }
  isSubmit = true
  http({
    url: http.adornUrl('/adminLogin'),
    method: 'post',
    data: http.adornData({
      userName: dataForm.value.userName,
      password: dataForm.value.password,
    }).then(({ data }) => {
      cookie.set('Authorization', data.accessToken)
      router.replace({ name: 'home' })
    }).catch(() => {
      isSubmit = false
    })

  })
}
</script>

<style lang="scss" scoped>
.login
{
  width: 100%;
  height: 100%;
  background: url("@/assets/img/login-bg.jpg") no-repeat;
  backgound-size: cover;
  position: fixed;
  .login-box
  {
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
    height: 100%;
    padding-top: 10%;
    .top
    {
      margin-bottom: 30px;
      text-align: center;
      .logo
      {
        font-size: 0;
        max-width: 50%;
        margin: 0 auto;
      }
    }
    .mid
    {
      font-size: 14px;
      .item-btn
      {
        width: 410px;
        margin-top: 20px;
        input
        {
          border: 0;
          width: 100%;
          height: 40px;
          border-radius: 3px;
        }
      }
    }
    .bottom
    {
      position: absolute;
      bottom: 10%;
      width: 100%;
      font-size: 12px;
      text-align: center;
    }
  }
}
.info
{
  width: 410px;
}
</style>