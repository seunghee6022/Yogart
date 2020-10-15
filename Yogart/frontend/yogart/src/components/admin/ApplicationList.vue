<template>
    <div>
        <ApplicationListItem v-for="application in applications" :key="application.id" :application="application" @permit="permit"/>
    </div>
</template>

<script>
import ApplicationListItem from './ApplicationListItem.vue'

export default {
    name: 'ApplicationList',
    components: {
        ApplicationListItem,
    },
    props: {
        applications: Array,
    },
    methods: {
        permit(userEmail) {
            this.$emit('permit', userEmail)
        }
    }
}
</script>

<style>

</style>