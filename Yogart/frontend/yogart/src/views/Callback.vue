<template>
  <div>
      잠시만 기다려주세요.
  </div>
</template>

<script>
import axios from 'axios'

const naverClientId = '4eWVX86Cci4TFZzNvA3b'
const naverCallbackUrl = "http://localhost:3000/naver/callback"
const naverDomainUrl = "http://localhost:3000"

export default {
  name : 'Callback',
  data() {
	  return {
		  SERVER_URL: this.$store.state.SERVER_URL,
	  }
  },
}
</script>

<style>

</style>