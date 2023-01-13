describe('Flight Search Test', () => {
  it('Search flight', () => {
    cy.visit('/')
    cy.get('[data-cy="app-name"]').should('contain', 'FlyAway')
    cy.get('[data-cy="flyFrom-input"]').should('contain', '')
    cy.get('[data-cy="flyFrom-input"]').type('Amster')
    cy.get(':nth-child(9) > :nth-child(1)').click()
    cy.get('[data-cy="flyTo-input"]').should('contain', '')
    cy.get('[data-cy="flyTo-input"]').type("Tel a")
    cy.get(':nth-child(14) > :nth-child(1)').click();
    cy.get(':nth-child(3) > .MuiInputBase-root > .MuiInputBase-input').type('2023-02-28')
    cy.get(':nth-child(4) > .MuiInputBase-root').type('2023-03-03')
    cy.get('.MuiBox-root > .MuiButtonBase-root').click()
    cy.get('.css-widufq > .MuiTypography-root').should('contain', 'Search Results')
    cy.get(':nth-child(1) > .css-v2lun0 > .css-zuyrli').should('contain', '€')
  })
})

describe('Login Test', () => {
  it('Login wrong email or password', () => {
    cy.visit('/Login')
    cy.get('#email').type('metodiHey')
    cy.get('#password').type('123456')
    cy.get('.MuiButtonBase-root').click()
    cy.get('.Toastify__toast-body > :nth-child(2)').should('contain', 'Incorrect Email or Password')
  })

  it('Login and logout successfully', () => {
    cy.visit('/Login')
    cy.get('#email').type('dumi@gmail.com')
    cy.get('#password').type('123123123')
    cy.get('.MuiButtonBase-root').click()
    cy.url().should('include', '/')
    cy.get('.css-1cqgw3v > [href="/"]').click()
    cy.get('.css-1cqgw3v > [href="/"]').should('not.exist')
  })
})
