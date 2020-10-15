<template>
    <div>
        <b-form-rating v-model="reviewData.rateValue"></b-form-rating>
        <p class="mt-2">Value: {{ reviewData.rateValue }}</p>
        <input v-model="reviewData.reviewContent" type="text"/>
        <button type="submit" @click="onSubmit"></button>
    </div>
</template>

<script>
import axios from 'axios'

export default {
    name: 'ReviewForm',
    props: {
        pastCourse: Object,
    },
    data() {
        return {
            reviewData: {
                rateValue: null,
                reviewContent: null,
                ptInfo: this.pastCourse,
            },
            SERVER_URL: this.$store.state.SERVER_URL,
        }
    },
    methods: {
        onSubmit(e) {
            e.preventDefault()
            const requestHeaders = {
                headers: {
                    Authorization: this.$cookies.get('auth-token')
                }
            }
            axios.post(this.SERVER_URL + '/api/review/create', this.reviewData, requestHeaders)
            .then(res => {
                console.log(res)
            })
            .catch(err => console.error(err))
        }
    }
}
</script>

<style>

</style>