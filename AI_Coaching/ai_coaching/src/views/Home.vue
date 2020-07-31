<template>
  <div class="home">

    <Loading/>
    <AI/>
    
    
    

  </div>
</template>

<script>
// @ is an alias to /src
import AI from '@/components/AI.vue'
import Loading from '@/components/Loading.vue'


export default {
  name: 'Home',
  components: {
    AI,
    Loading,
   
  }
}

    

</script>
