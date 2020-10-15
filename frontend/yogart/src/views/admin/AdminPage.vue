<template>
    <div class="mt-5">
        <h2>요가 강사 자격증명</h2>
        <ApplicationList @permit="setTeacher" :applications="applications"/>
    </div>
</template>

<script>
import axios from 'axios'
import ApplicationList from '@/components/admin/ApplicationList.vue'

export default {
    name: 'AdminPage',
    components: {
        ApplicationList,
    },
    data() {
        return {
            SERVER_URL: this.$store.state.SERVER_URL,
            applications: [],
        }
    },
    methods: {
        setTeacher(userEmail) {
            axios.get(this.SERVER_URL + '/api/users/registration?userEmail=' + userEmail)
            .then(res => {
                this.applications = res.data
            })
            .catch(err => console.error(err))
        }
    },
    mounted() {
        const requestHeaders = {
            headers: {
                Authorization: this.$cookies.get('auth-token')
            }
        }
        axios.get(this.SERVER_URL + '/api/users/registrationList', requestHeaders)
        .then(res => {
            this.applications = res.data
        })
        .catch(err => console.error(err))
    }
}
</script>

<style>

</style>