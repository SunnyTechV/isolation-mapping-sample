import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'isolation',
        data: { pageTitle: 'isolationmappingApp.isolation.home.title' },
        loadChildren: () => import('./isolation/isolation.module').then(m => m.IsolationModule),
      },
      {
        path: 'isolation-details',
        data: { pageTitle: 'isolationmappingApp.isolationDetails.home.title' },
        loadChildren: () => import('./isolation-details/isolation-details.module').then(m => m.IsolationDetailsModule),
      },
      {
        path: 'assessment',
        data: { pageTitle: 'isolationmappingApp.assessment.home.title' },
        loadChildren: () => import('./assessment/assessment.module').then(m => m.AssessmentModule),
      },
      {
        path: 'question',
        data: { pageTitle: 'isolationmappingApp.question.home.title' },
        loadChildren: () => import('./question/question.module').then(m => m.QuestionModule),
      },
      {
        path: 'questions-options',
        data: { pageTitle: 'isolationmappingApp.questionsOptions.home.title' },
        loadChildren: () => import('./questions-options/questions-options.module').then(m => m.QuestionsOptionsModule),
      },
      {
        path: 'assessment-answer',
        data: { pageTitle: 'isolationmappingApp.assessmentAnswer.home.title' },
        loadChildren: () => import('./assessment-answer/assessment-answer.module').then(m => m.AssessmentAnswerModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
