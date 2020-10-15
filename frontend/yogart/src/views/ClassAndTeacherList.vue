<template>
    <div class="m-5">
         <b-tabs content-class="mt-3">
            <b-tab title="수업 목록" active><ClassList /></b-tab>
            <b-tab title="강사 목록"><TeacherList /></b-tab>
        </b-tabs>
    </div>
</template>

<script>
import ClassList from '../components/ClassList.vue'
import TeacherList from './teacher/TeacherList.vue'

export default {
    name: 'ClassAndTeacherList',
    data() {
        return {
           
        }
    },
    components: {
        ClassList,
        TeacherList
    },
    
    methods: {
        
    },
}
</script>

<style>
.nav-link {
    color: gray !important;
    position: relative;
    font-size: 1.2rem;
    font-weight: 500;
    padding: 1rem !important;
    cursor: pointer;
}
.nav-link:after {    
  content: "";
  display: block;
  height: 4px;
  background: #8fa0f2;
  transition: width 0.7s ease 0s, left 0.3s ease 0s;
  width: 0;
}
.nav-link:hover:after { 
  width: 10rem !important;
}

</style>